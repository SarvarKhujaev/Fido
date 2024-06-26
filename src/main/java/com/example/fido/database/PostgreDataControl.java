package com.example.fido.database;

import java.sql.*;
import java.util.Arrays;
import java.text.MessageFormat;

import com.example.fido.constants.PostgreSqlTables;
import com.example.fido.constants.PostgresBufferMethods;
import com.example.fido.constants.PostgresVacuumMethods;

import com.example.fido.FidoApplication;
import com.example.fido.interfaces.ServiceCommonMethods;
import com.example.fido.components.CollectionsInspector;

public final class PostgreDataControl extends CollectionsInspector implements ServiceCommonMethods {
    private Connection connection;
    private static PostgreDataControl INSTANCE = new PostgreDataControl();

    private Connection getConnection() {
        return this.connection;
    }

    public static PostgreDataControl getInstance () {
        return INSTANCE != null ? INSTANCE : ( INSTANCE = new PostgreDataControl() );
    }

    private PostgreDataControl () {
        try {
            // подключаемся к базе
            this.connection = DriverManager.getConnection(
                    FidoApplication
                            .context
                            .getEnvironment()
                            .getProperty( "variables.POSTGRES_VARIABLES.URL" ),
                    FidoApplication
                            .context
                            .getEnvironment()
                            .getProperty( "variables.POSTGRES_VARIABLES.USER" ),
                    FidoApplication
                            .context
                            .getEnvironment()
                            .getProperty( "variables.POSTGRES_VARIABLES.PASSWORD" )
            );

            this.getConnection().setTransactionIsolation( Connection.TRANSACTION_READ_COMMITTED );

            this.prewarmTable();

            super.analyze(
                    super.newList( PostgreSqlTables.values() ),
                    this::insertTableContentToBuffer
            );

            // выводим сообщение об успехе
            super.logging( this.getClass() );
        } catch ( final SQLException e ) {
            super.logging( e );
            this.close();
        }
    }

    @Override
    public void prewarmTable () {
        try (
                final PreparedStatement preparedStatement = this.getConnection().prepareStatement(
                        PostgresBufferMethods.PREWARM_TABLE
                )
        ) {
            preparedStatement.executeQuery();
        } catch ( final SQLException e ) {
            super.logging( e );
        }
    }

    @Override
    public void vacuumTable (
            final PostgreSqlTables table
    ) {
        try (
                final PreparedStatement preparedStatement = this.getConnection().prepareStatement(
                        MessageFormat.format(
                                """
                                "VACUUM( {0} ) {1};"
                                """,
                                PostgresVacuumMethods.ANALYZE,
                                table
                        )
                )
        ) {
            preparedStatement.executeQuery();
        } catch ( final SQLException e ) {
            super.logging( e );
        }
    }

    @Override
    public void insertTableContentToBuffer(
            final PostgreSqlTables table
    ) {
        try (
                final PreparedStatement preparedStatement = this.getConnection().prepareStatement(
                        PostgresBufferMethods.INSERT_TABLE_CONTENT_INTO_BUFFER.formatted(
                                table
                        )
                )
        ) {
            super.logging(
                    String.valueOf( preparedStatement.executeQuery().getInt( "pg_prewarm" ) )
            );
        } catch ( final SQLException e ) {
            super.logging( e );
        }
    }

    /*
    выводим данные о потреблении кеша буферизации
    для конкретной таблицы
     */
    @Override
    public void calculateBufferForTable(
            final PostgreSqlTables table
    ) {
        try (
                final PreparedStatement preparedStatement = this.getConnection().prepareStatement(
                        PostgresBufferMethods.SELECT_BUFFER_ANALYZE_FOR_TABLE.formatted(
                                table
                        )
                )
        ) {
            final ResultSet resultSet = preparedStatement.executeQuery();

            super.logging(
                    String.join(
                            " : ",
                            String.valueOf( resultSet.getLong( "bufferid" ) ),
                            resultSet.getString( "relname" ),
                            resultSet.getString( "relfork" ),
                            String.valueOf( resultSet.getInt( "relblocknumber" ) ),
                            String.valueOf( resultSet.getBoolean( "isdirty" ) ),
                            String.valueOf( resultSet.getInt( "usagecount" ) )
                    )
            );
        } catch ( final SQLException e ) {
            super.logging( e );
        }
    }

    // закрывает подключение к базе
    @Override
    public void close () {
        try {
            super.analyze(
                    Arrays.asList( PostgreSqlTables.values() ),
                    table -> {
                        this.calculateBufferForTable( table );
                        this.vacuumTable( table );
                    }
            );

            this.getConnection().close();
            super.logging( this );
        } catch ( final Exception e ) {
            super.logging( e );
        }
    }
}
