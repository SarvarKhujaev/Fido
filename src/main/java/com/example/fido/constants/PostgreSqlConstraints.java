package com.example.fido.constants;

public enum PostgreSqlConstraints {
    entities_patruls_rank_must_be_unique,
    entities_patruls_email_must_be_unique,
    entities_patruls_organName_must_be_unique,
    entities_patruls_policeType_must_be_unique,
    entities_patruls_dateOfBirth_must_be_unique,
    entities_patruls_passport_number_must_be_unique,
    entities_patruls_patrul_image_link_must_be_unique,
    entities_patruls_auth_table_password_cannot_be_empty,

    escort_countries_flag_cannot_be_empty,
    escort_countries_symbol_cannot_be_empty,
    escort_countries_country_name_uz_cannot_be_empty,
    escort_countries_country_name_ru_cannot_be_empty,

    car_latitude_must_be_larger_than_zero,
    car_longitude_must_be_larger_than_zero,
}
