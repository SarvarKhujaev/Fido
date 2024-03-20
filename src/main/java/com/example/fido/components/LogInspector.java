package com.example.fido.components;

import com.clickhouse.client.ClickHouseResponseSummary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
отвечает за вывод различных логов
использует библиотеку jackson-dataformat-yaml
 */
public class LogInspector extends DataValidateInspector {
    private final Logger LOGGER = LogManager.getLogger( "LOGGER_WITH_JSON_LAYOUT" );

    private Logger getLOGGER() {
        return this.LOGGER;
    }

    protected void logging ( final Class clazz ) {
        this.getLOGGER().info( clazz.getName() + " was created at: " + super.newDate() );
    }

    protected void logging ( final Object o ) {
        this.getLOGGER().info( o.getClass().getName() + " was closed successfully at: " + super.newDate() );
    }

    protected final void logging ( final String message ) {
        this.getLOGGER().info( message );
    }

    protected final void logging ( final ClickHouseResponseSummary responseSummary ) {
        this.getLOGGER().info( responseSummary.getTotalRowsToRead() );
    }

    protected final void logging ( final Throwable error ) {
        this.getLOGGER().error( "Error: " + error );
    }
}
