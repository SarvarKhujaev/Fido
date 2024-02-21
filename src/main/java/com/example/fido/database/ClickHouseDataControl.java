package com.example.fido.database;

import java.sql.*;
import java.util.Properties;

import com.example.fido.components.ErrorInspector;
import com.example.fido.constants.PostgreSqlTables;
import com.example.fido.constants.PostgreTablesOperations;

/*
отвечает за работу с БД ClickHouse
*/
public final class ClickHouseDataControl extends ErrorInspector {
    private Connection connection;

    private static final String DB_URL = "jdbc:clickhouse://localhost:8123/default";

    public ClickHouseDataControl () {
        try {
            final Properties properties = new Properties();
            properties.setProperty( "user", "default" );
            properties.setProperty( "password", "killerbee1998" );

            this.connection = DriverManager.getConnection( DB_URL, properties );

            DatabaseRegisterTablesAndTypes.register( connection );
        } catch ( final SQLException e ) {
            super.logging( e );
        }
    }
}
