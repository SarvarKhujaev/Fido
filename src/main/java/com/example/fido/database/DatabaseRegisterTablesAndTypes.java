package com.example.fido.database;

import com.clickhouse.client.*;
import java.text.MessageFormat;

import com.example.fido.constants.*;
import com.example.fido.components.ErrorInspector;
import com.example.fido.constants.clickhouse.Categories;
import com.example.fido.constants.clickhouse.ClickHouseEngines;
import com.example.fido.constants.clickhouse.ClickHouseFunctions;
import com.example.fido.constants.clickhouse.ClickHouseDateFunctions;

/*
создает БД, типы и таблицы
*/
public final class DatabaseRegisterTablesAndTypes extends ErrorInspector {
    private final ClickHouseNode server;
    private final ClickHouseClient clickHouseClient;

    public static void register (
            final ClickHouseNode clickHouseNode,
            final ClickHouseClient clickHouseClient
    ) {
        new DatabaseRegisterTablesAndTypes( clickHouseNode, clickHouseClient );
    }

    private DatabaseRegisterTablesAndTypes (
            final ClickHouseNode clickHouseNode,
            final ClickHouseClient clickHouseClient
    ) {
        this.clickHouseClient = clickHouseClient;
        this.server = clickHouseNode;
        this.createDatabase();
        this.createTables();
    }

    private void createDatabase () {
        try {
            final ClickHouseResponse response = this.clickHouseClient.connect( this.server )
                    .write()
                    .format( ClickHouseFormat.RowBinary )
                    .query(
                            PostgreTablesOperations.CREATE_DATABASE.formatted(
                                    PostgreSqlSchema.TABLETS.name().toLowerCase()
                            )
                    ).executeAndWait();

            response.close();
        } catch ( final ClickHouseException exception ) {
            super.logging( exception );
        }
    }

    private void createTables () {
        try {
            ClickHouseResponse response = this.clickHouseClient.connect( this.server )
                    .write()
                    .format( ClickHouseFormat.RowBinary )
                    .query(
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
                    ).executeAndWait();

            response.close();

            response = this.clickHouseClient.connect( this.server )
                    .write()
                    .format( ClickHouseFormat.RowBinary )
                    .query(
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

                                    PostgreSqlSchema.TABLETS.name().toLowerCase(),
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
                    ).executeAndWait();

            response.close();

            response = this.clickHouseClient.connect( this.server )
                    .write()
                    .format( ClickHouseFormat.RowBinary )
                    .query(
                            MessageFormat.format(
                                    """
                                    {0} {1}.{2}
                                    (
                                        name                {3} {8} {9}, -- название видео
                                        description         {3} {8} {9}, -- описание видео
        
                                        likes               {4} {8} {10}, -- количество лайков видео
                                        watches             {4} {8} {10}, -- количество просмотров видео
                                        dislikes            {4} {8} {10}, -- количество дизлайков видео
                                        duration            {4} {8} {11}, -- длительность видео
                                        limit_age           {4} {8} {12}, -- ограничение по возрасту для видео
        
                                        id                  {5} {8} {13},
                                        author              {5} {14}, -- id владельца
        
                                        tags                {6}( {3} ),
                                        created_at          {7} {8} {15} -- дата создания
                                    ) ENGINE = {16}
                                    ORDER BY ( author, name, created_at )
                                    PARTITION BY toYYYYMM( created_at )
                                    """,
                                    PostgreTablesOperations.CREATE_TABLE,

                                    PostgreSqlSchema.TABLETS.name().toLowerCase(),
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
                    ).executeAndWait();

            response.close();

            response = this.clickHouseClient.connect( this.server )
                    .write()
                    .format( ClickHouseFormat.RowBinary )
                    .query(
                            MessageFormat.format(
                                    """
                                    {0} {1}.{2}
                                    (
                                        name                {3} {7}, -- имя клиента
                                        shop_name           {3} {7}, -- название магазин, которым он владеет
        
                                        id                  {4} {8} {9},
                                        total_count         {5} {8} {11}, -- количество купленных товаров
                                        registered_at       {6} {8} {10} -- дата регистрации
                                    ) ENGINE = {12}
                                    ORDER BY ( id, shop_name, registered_at )
                                    PARTITION BY toYYYYMM( registered_at )
                                    """,
                                    PostgreTablesOperations.CREATE_TABLE,

                                    PostgreSqlSchema.TABLETS.name().toLowerCase(),
                                    PostgreSqlTables.OWNER.name().toLowerCase(),

                                    ClickHouseDataType.String,
                                    ClickHouseDataType.UUID,
                                    ClickHouseDataType.UInt8,
                                    ClickHouseDataType.DateTime,

                                    PostgreTablesOperations.NOT_NULL,
                                    PostgreTablesOperations.DEFAULT,

                                    ClickHouseFunctions.UUID_GENERATOR,
                                    ClickHouseDateFunctions.NOW,

                                    0,
                                    ClickHouseEngines.MergeTree
                            )
                    ).executeAndWait();

            response.close();

            response = this.clickHouseClient.connect( this.server )
                    .write()
                    .format( ClickHouseFormat.RowBinary )
                    .query(
                            MessageFormat.format(
                                    """
                                    {0} {1}.{2}
                                    (
                                        name                {3} {9} {10} ''{11}'', -- название продукта
                                        category            {3} {9} {10} ''{12}'', -- категория продукта
                                        description         {3} {9} {10} ''{11}'', -- подробное описание продукта
        
                                        likes               {4} {9} {10} {13}, -- количество лайков продукта
                                        watches             {4} {9} {10} {13}, -- количество просмотров продукта
                                        returned            {4} {9} {10} {13}, -- количество возвратов продукта
                                        dislikes            {4} {9} {10} {13}, -- количество дизлайков продукта
                                        was_sold            {4} {9} {10} {13}, -- количество продаж
                                        limit_age           {4} {9} {10} {14}, -- ограничение на возраст при котором можно купить товар
                                        total_count         {4} {9} {10} {13}, -- общее количество продукта
                                        rating              {5} {9} {10} {15}, -- рейтинг товара, от 0.0 до 5.0
        
                                        id                  {6} {10} {16},
                                        owner              {6} {9}, -- id владельца
        
                                        tags                {7}( {3} ), -- теги товара по которым, можно найти товар
                                        created_at          {8} {9} {10} {17} -- дата создания
                                    ) ENGINE = {18}
                                    ORDER BY ( name, category, owner, rating, created_at )
                                    PARTITION BY category
                                    """,
                                    PostgreTablesOperations.CREATE_TABLE,

                                    PostgreSqlSchema.TABLETS.name().toLowerCase(),
                                    PostgreSqlTables.PRODUCT.name().toLowerCase(),

                                    ClickHouseDataType.String,
                                    ClickHouseDataType.UInt8,
                                    ClickHouseDataType.Float32,
                                    ClickHouseDataType.UUID,
                                    ClickHouseDataType.Array,
                                    ClickHouseDataType.DateTime,

                                    PostgreTablesOperations.NOT_NULL,
                                    PostgreTablesOperations.DEFAULT,

                                    "just some video",
                                    Categories.ADULT_PRODUCT,
                                    0,
                                    18,
                                    5.0,

                                    ClickHouseFunctions.UUID_GENERATOR,
                                    ClickHouseDateFunctions.NOW,

                                    ClickHouseEngines.MergeTree
                            )
                    ).executeAndWait();

            response.close();

            response = this.clickHouseClient.connect( this.server )
                    .write()
                    .format( ClickHouseFormat.RowBinary )
                    .query(
                            MessageFormat.format(
                                    """
                                    {0} {1}.{2}
                                    (
                                        content         {3} {6}, -- описание продукта
                                        created_at      {4} {6} {7} {8}, -- дата создания
        
                                        id              {5} {7} {9}, -- id продукта
                                        author          {5} {6}, -- id автора
                                        product         {5} {6} -- id продукта к которому, оставили комментарий
                                    ) ENGINE = {10}
                                    ORDER BY ( product, author, created_at )
                                    PARTITION BY product
                                    """,
                                    PostgreTablesOperations.CREATE_TABLE,

                                    PostgreSqlSchema.TABLETS.name().toLowerCase(),
                                    PostgreSqlTables.COMMENT.name().toLowerCase(),

                                    ClickHouseDataType.String,
                                    ClickHouseDataType.DateTime,
                                    ClickHouseDataType.UUID,

                                    PostgreTablesOperations.NOT_NULL,
                                    PostgreTablesOperations.DEFAULT,

                                    ClickHouseDateFunctions.NOW,
                                    ClickHouseFunctions.UUID_GENERATOR,
                                    ClickHouseEngines.MergeTree
                            )
                    ).executeAndWait();

            response.close();
        } catch ( final ClickHouseException exception ) {
            super.logging( exception );
        }
    }
}
