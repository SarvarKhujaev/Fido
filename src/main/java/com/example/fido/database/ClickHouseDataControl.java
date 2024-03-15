package com.example.fido.database;

import java.util.*;
import com.clickhouse.client.*;

import com.example.fido.FidoApplication;
import com.example.fido.components.ErrorInspector;
import com.example.fido.constants.PostgreCommands;
import com.example.fido.constants.PostgreSqlTables;
import com.example.fido.constants.clickhouse.Categories;
import com.example.fido.interfaces.ServiceCommonMethods;

/*
отвечает за работу с БД ClickHouse
*/
public final class ClickHouseDataControl extends ErrorInspector implements ServiceCommonMethods {
    private final ClickHouseNode server;
    private final ClickHouseClient clickHouseClient;

    private static ClickHouseDataControl INSTANCE = new ClickHouseDataControl();

    public static ClickHouseDataControl getInstance() {
        return INSTANCE != null ? INSTANCE : ( INSTANCE = new ClickHouseDataControl() );
    }

    private ClickHouseDataControl () {
        /*
        подключаемся в самой БД
         */
        this.server = ClickHouseNode
                .builder()
                .host( FidoApplication
                        .context
                        .getEnvironment()
                        .getProperty( "variables.CLICKHOUSE_VARIABLES.HOST" ) )
                .port(
                        ClickHouseProtocol.HTTP,
                        Integer.valueOf(
                                FidoApplication
                                        .context
                                        .getEnvironment()
                                        .getProperty( "variables.CLICKHOUSE_VARIABLES.PORT" )
                        )
                ).database(
                        FidoApplication
                                .context
                                .getEnvironment()
                                .getProperty( "variables.CLICKHOUSE_VARIABLES.DATABASE" )
                )
                .credentials(
                        ClickHouseCredentials.fromUserAndPassword(
                                FidoApplication
                                        .context
                                        .getEnvironment()
                                        .getProperty( "variables.CLICKHOUSE_VARIABLES.USER" ),
                                FidoApplication
                                        .context
                                        .getEnvironment()
                                        .getProperty( "variables.CLICKHOUSE_VARIABLES.PASSWORD" )
                        )
                ).build();

        /*
        создаем клиента для дальнейших операций с БД
         */
        this.clickHouseClient = ClickHouseClient.newInstance( this.server.getProtocol() );

        /*
        создаем все БД, таблицы и т.д
         */
        DatabaseRegisterTablesAndTypes.register( this.server, this.clickHouseClient );

        super.logging( this.getClass() );
    }

    public void insertValues () {
        try {
            final List< Categories > categories = List.of( Categories.values() );
            final List< List< String > > tags = List.of(
                    List.of(
                            "'Kuplinov'",
                            "'Dangar'",
                            "'UFA'"
                    ),

                    List.of(
                            "'TEST'",
                            "'Kuplinov'",
                            "'Dangar'"
                    ),

                    List.of(
                            "'Kuplinov'",
                            "'UFA'"
                    ),

                    List.of(
                            "'Dangar'",
                            "'UFA'",
                            "'TEST'"
                    ),

                    List.of(
                            "'Dangar'",
                            "'UFA'"
                    )
            );

            final List< UUID > author = List.of(
                    UUID.fromString( "340e672a-552a-4f6e-bffc-0a0790984161" ),
                    UUID.fromString( "1b94c535-94c3-4b0f-bfee-53979e088d59" ),
                    UUID.fromString( "7e400cd2-44a2-4c7d-bfdc-68a38262c61d" )
            );

            final List< UUID > product = List.of(
                    UUID.fromString( "8c4c6598-9b86-4cec-b5d5-7e4a6e8476f4" ),
                    UUID.fromString( "6230e2e6-a480-4cee-b9b7-3b217300e156" ),
                    UUID.fromString( "abe24365-009f-41e0-bfa3-9c7a21c85792" )
            );

            final Random random = new Random();

            final StringBuilder stringBuilder = new StringBuilder(
                    PostgreCommands.INSERT.formatted(
                            PostgreSqlTables.TABLETS.name().toLowerCase(),
                            PostgreSqlTables.PRODUCT.name().toLowerCase(),
                            """
                            tags,
                            owner,
                            category,
                            total_count,
                            returned,
                            dislikes,
                            was_sold,
                            watches,
                            likes,
                            limit_age,
                            rating
                            """
                    )
            );

            for ( int i = 0; i < 3_000_000; i++ ) {
                stringBuilder.append( "(" )
                        .append( tags.get( random.nextInt( 0, tags.size() - 1 ) ) )
                        .append( ", '" )
                        .append( author.get( random.nextInt( 0, author.size() - 1 ) ) )
                        .append( "', '" )
                        .append( categories.get( random.nextInt( 0, categories.size() - 1 ) ) )
                        .append( "', " )
                        .append( random.nextInt( 1_000_000 ) )
                        .append( ", " )
                        .append( random.nextInt( 1_000_000 ) )
                        .append( ", " )
                        .append( random.nextInt( 1_000_000 ) )
                        .append( ", " )
                        .append( random.nextInt( 1_000_000 ) )
                        .append( ", " )
                        .append( random.nextInt( 1_000_000 ) )
                        .append( ", " )
                        .append( random.nextInt( 1_000_000 ) )
                        .append( ", " )
                        .append( random.nextInt( 18, 61 ) )
                        .append( ", " )
                        .append( random.nextFloat( 0, 6 ) )
                        .append( ")" );
            }

            try (
                final ClickHouseResponse response = this.clickHouseClient.connect( this.server )
                        .write()
                        .table(
                                PostgreSqlTables.TABLETS.name().toLowerCase()
                                + "."
                                + PostgreSqlTables.PRODUCT.name().toLowerCase()
                        ).format( ClickHouseFormat.RowBinary )
                        .query( stringBuilder.toString() )
                        .executeAndWait()
            ) {
                final ClickHouseResponseSummary summary = response.getSummary();
                System.out.println( summary.getWrittenRows() );
            }

            tags.clear();
            author.clear();
            product.clear();
            categories.clear();

        } catch ( final Exception exception ) {
            super.logging( exception );
        }
    }

    public void make_query () {
        try (
            final ClickHouseResponse response = this.clickHouseClient.connect( this.server )
                    .format( ClickHouseFormat.RowBinaryWithNamesAndTypes )
                    .query( "SELECT :category, COUNT(*) FROM :tablets.:product GROUP BY :category;" )
                    .params(
                            "category",
                            PostgreSqlTables.TABLETS.name().toLowerCase(),
                            PostgreSqlTables.PRODUCT.name().toLowerCase() )
                    .executeAndWait()
        ) {
            response.records().forEach( record -> System.out.println(
                    record.getValue( "category" ).asString()
            ) );

            final ClickHouseResponseSummary summary = response.getSummary();
            System.out.println( summary.getTotalRowsToRead() );

        } catch ( final ClickHouseException e ) {
            super.logging( e );
        }
    }

    @Override
    public void close () {
        INSTANCE = null;
        this.clickHouseClient.close();

        super.logging( this.getClass() );
    }
}
