package com.example.fido.constants.clickhouse;

public enum ClickHouseEngines {
    // Log family
    Log,
    TinyLog,
    StripeLog,

    // Integration Engines family
    S3,
    ODBC,
    JDBC,
    HDFS,
    MySQL,
    Kafka,
    Redis,
    MongoDB,
    S3Queue,
    RabbitMQ,
    PostgreSQL,
    EmbeddedRocksDB,

    // Special Engines
    Set,
    URL,
    File,
    Null,
    Join,
    View,
    Merge,
    Memory,
    Buffer,
    KeeperMap,
    Dictionary,
    Distributed,
    MaterializedView,

    // MergeTree family
    MergeTree,
    SummingMergeTree,
    GraphiteMergeTree,
    ReplacingMergeTree,
    CollapsingMergeTree,
    AggregatingMergeTree,
    VersionedCollapsingMergeTree,
}
