package com.example.fido.interfaces;

import com.example.fido.constants.PostgreSqlTables;

public interface ServiceCommonMethods {
    /*
    переносим все накопленные данные
    в Буферный кэш
    */
    default void insertTableContentToBuffer (
            final PostgreSqlTables table
    ) {}

    /*
    очищаем таблицу от старых и не используемых записей
    */
    default void vacuumTable (
            final PostgreSqlTables table
    ) {}

    default void prewarmTable () {};

    void close();
}
