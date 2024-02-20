package com.example.fido.components;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/*
отвечает за вывод различных логов
использует библиотеку jackson-dataformat-yaml
 */
public class LogInspector extends DataValidateInspector {
    private final Logger LOGGER = LogManager.getLogger( "LOGGER_WITH_JSON_LAYOUT" );

    private Logger getLOGGER() { return this.LOGGER; }

    protected final void logging ( final String message ) { this.getLOGGER().info( message ); }

    protected final void logging ( final Throwable error ) { this.getLOGGER().error( "Error: " + error ); }

    protected void logging ( final Throwable error, final Object o ) { this.getLOGGER().error("Error: {} and reason: {}: ",
            error.getMessage(), o ); }
}
