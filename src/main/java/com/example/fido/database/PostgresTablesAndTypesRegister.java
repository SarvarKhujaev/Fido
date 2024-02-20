package com.example.fido.database;

import com.example.fido.components.LogInspector;

import java.sql.Connection;

public final class PostgresTablesAndTypesRegister extends LogInspector {
    private final Connection connection;

    private Connection getConnection () {
        return this.connection;
    }

    public static void generate (
            final Connection connection
    ) {
        new PostgresTablesAndTypesRegister( connection );
    }

    private PostgresTablesAndTypesRegister (
            final Connection connection
    ) {
        this.connection = connection;
    }
}
