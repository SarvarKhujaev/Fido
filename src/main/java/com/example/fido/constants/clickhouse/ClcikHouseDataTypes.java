package com.example.fido.constants.clickhouse;

public enum ClcikHouseDataTypes {
    UInt8,
    UInt16,
    UInt32,
    UInt64,
    UInt128,
    UInt256,

    Int8,
    Int16,
    Int32,
    Int64,
    Int128,
    Int256,

    Float32,
    Float64,

    Bool,

    STRING,
    FixedString,

    Date,
    Date32,
    DateTime,
    DateTime64,

    JSON,
    UUID,
    Enum,

    MAP,
    SET,
    ARRAY,
    TUPLE,

    Nothing,
    Nullable,
    Interval,
    Expression,
}
