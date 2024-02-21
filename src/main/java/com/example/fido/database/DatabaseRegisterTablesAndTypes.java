package com.example.fido.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.MessageFormat;

import com.example.fido.constants.*;
import com.clickhouse.client.ClickHouseDataType;
import com.example.fido.components.ErrorInspector;
import com.example.fido.constants.clickhouse.ClickHouseEngines;
import com.example.fido.constants.clickhouse.ClickHouseFunctions;
import com.example.fido.constants.clickhouse.ClickHouseDateFunctions;

/*
создает БД, типы и таблицы
 */
public final class DatabaseRegisterTablesAndTypes extends ErrorInspector {
    private final Connection connection;

    public static void register (
            final Connection connection
    ) {
        new DatabaseRegisterTablesAndTypes( connection );
    }

    private DatabaseRegisterTablesAndTypes (
            final Connection connection
    ) {
        this.connection = connection;
        this.createDatabase();
        this.createTables();
    }

    private void createDatabase () {
        try {
            this.connection.createStatement().execute(
                    PostgreTablesOperations.CREATE_DATABASE.formatted(
                            PostgreSqlTables.TABLETS.name().toLowerCase()
                    )
            );
        } catch ( final SQLException e ) {
            System.out.println( e.getMessage() );
        }
    }

    private void createTables () {
        try {
            this.connection.createStatement().execute(
                    MessageFormat.format(
                        """
                            {0} {1}.{2} (
                                id                  {3} {6} {15},
                                age                 {4} {6} 10,

                                name                {5} {7},
                                email               {5} {7},

                                registration_date   {8} {6} {9},
                                {10} {11} {13} age >= 18,
                                {10} {12} {13} length( name ) >= 18,
                            ) ENGINE = {14}
                            ORDER BY ( id, registration_date, age )
                            PARTITION BY toYYYYMM( registration_date );
                            """,
                            PostgreTablesOperations.CREATE_TABLE,

                            PostgreSqlSchema.TABLETS.name().toLowerCase(),
                            PostgreSqlTables.USER.name().toLowerCase(),

                            ClickHouseDataType.UUID,
                            ClickHouseDataType.UInt8,
                            ClickHouseDataType.String,

                            PostgreTablesOperations.DEFAULT,
                            PostgreTablesOperations.NOT_NULL,

                            ClickHouseDataType.DateTime,
                            ClickHouseDateFunctions.NOW,

                            PostgreTablesOperations.CONSTRAINT,

                            PostgreSqlConstraints.check_age,
                            PostgreSqlConstraints.check_name,

                            PostgreTablesOperations.CHECK,

                            ClickHouseEngines.MergeTree,

                            ClickHouseFunctions.UUID_GENERATOR
                    )
            );

            this.connection.createStatement().execute(
                    MessageFormat.format(
                            """
                            {0} {1}.{2}
                            (
                                id                      {3} {6} {12},
                                type                    {4} {6} ''DEBUG'',
                                registration_date       {5} {6} {7},
                                {8} {9} {10} registration_date <= {6}
                            ) ENGINE = {11}
                            ORDER BY ( id, registration_date, type )
                            PARTITION BY toYYYYMM( registration_date );
                            """,
                            PostgreTablesOperations.CREATE_TABLE,

                            PostgreSqlTables.TABLETS.name().toLowerCase(),
                            PostgreSqlTables.LOG.name().toLowerCase(),

                            ClickHouseDataType.UUID,
                            ClickHouseDataType.String,
                            ClickHouseDataType.DateTime,

                            PostgreTablesOperations.DEFAULT,

                            ClickHouseDateFunctions.NOW,

                            PostgreTablesOperations.CONSTRAINT,
                            PostgreSqlConstraints.check_data,
                            PostgreTablesOperations.CHECK,

                            ClickHouseEngines.MergeTree,

                            ClickHouseFunctions.UUID_GENERATOR
                    )
            );

            this.connection.createStatement().execute(
                    MessageFormat.format(
                            """
                            {0} {1}.{2}
                            (
                                name                {3} {8} {9},
                                description         {3} {8} {9},
            
                                likes               {4} {8} {10},
                                watches             {4} {8} {10},
                                dislikes            {4} {8} {10},
                                duration            {4} {8} {11},
                                limit_age           {4} {8} {12},
            
                                id                  {5} {8} {13},
                                author              {5} {14},
            
                                tags                {6}( {3} ),
                                created_at          {7} {8} {15}
                            ) ENGINE = {16}
                            ORDER BY ( author, name, created_at )
                            PARTITION BY toYYYYMM( created_at )
                            """,
                            PostgreTablesOperations.CREATE_TABLE,

                            PostgreSqlTables.TABLETS.name().toLowerCase(),
                            PostgreSqlTables.VIDEO.name().toLowerCase(),

                            ClickHouseDataType.String,
                            ClickHouseDataType.UInt8,
                            ClickHouseDataType.UUID,
                            ClickHouseDataType.Array,
                            ClickHouseDataType.DateTime,

                            PostgreTablesOperations.DEFAULT,

                            "'just some video'",
                            0,
                            120,
                            18,

                            ClickHouseFunctions.UUID_GENERATOR,

                            PostgreTablesOperations.NOT_NULL,

                            ClickHouseDateFunctions.NOW,

                            ClickHouseEngines.MergeTree
                    )
            );
        } catch ( final SQLException exception ) {
            super.logging( exception );
        }
    }
}
