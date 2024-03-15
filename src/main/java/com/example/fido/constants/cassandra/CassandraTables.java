package com.example.fido.constants.cassandra;

public enum CassandraTables {
    // tables for CRUD
    TABLETS,
    REPORT_FOR_CARD,
    POLYGON_FOR_PATRUl,
    TABLETS_USAGE_TABLE,

    CARS,
    LUSTRA,
    PATRULS,
    POLYGON,
    NOTIFICATION,
    PATRULS_LOGIN_TABLE,
    PATRULS_STATUS_TABLE,

    PATRUL_TYPE,
    POLICE_TYPE,
    CAMERA_LIST,
    POLYGON_TYPE,
    POLYGON_ENTITY,
    VIOLATION_LIST_TYPE,

    // tables for Tasks
    TASKS_STORAGE_TABLE,
    FACECAR, EVENTCAR, EVENTFACE, EVENTBODY, FACEPERSON,
    ACTIVE_TASK, CARTOTALDATA, TASKS_TIMING_TABLE,

    // tables for ESCORT Entity
    ESCORT,
    COUNTRIES, TUPLE_OF_CAR, TUPLE_OF_ESCORT, POINTS_ENTITY, POLYGON_FOR_ESCORT,

    // tables for TRACKERS
    TRACKERS, TRACKERSID,

    // tables for TABLETS
    GPSTABLETS,
    TABLETS_LOCATION_TABLE, POSITION_INFO,

    // For Sos entity
    SOS_TABLE, PATRUL_SOS_TABLE,
    PATRUL_SOS_LIST, // <- хранит список всех сос сигналов которые принял патрульный

    // tables for Android version control
    ANDROID_VERSION_CONTROL_TABLE, // <- хранит последнюю версию андроид приложения

    PATRUL_CAR_DATA,
    PATRUL_FIO_DATA,
    PATRUL_TASK_DATA,
    PATRUL_DATE_DATA,
    PATRUL_AUTH_DATA,
    PATRUL_TOKEN_DATA,
    PATRUL_REGION_DATA,
    PATRUL_MOBILE_DATA,
    PATRUL_UNIQUE_DATA,
    PATRUL_LOCATION_DATA,
}
