package com.example.fido.constants;
/*
хранит все команды для работы с ролями
*/
public final class PostgresRolesCommands {
    /*
    CREATE ROLE name [ [ WITH ] parameter [ ... ] ]

    SUPERUSER | NOSUPERUSER
    | CREATEDB | NOCREATEDB
    | CREATEROLE | NOCREATEROLE
    | INHERIT | NOINHERIT
    | LOGIN | NOLOGIN
    | REPLICATION | NOREPLICATION
    | BYPASSRLS | NOBYPASSRLS
    | CONNECTION LIMIT connlimit
    | [ ENCRYPTED ] PASSWORD 'password' | PASSWORD NULL
    | VALID UNTIL 'timestamp'
    | IN ROLE role_name [, ...]
    | IN GROUP role_name [, ...]
    | ROLE role_name [, ...]
    | ADMIN role_name [, ...]
    | USER role_name [, ...]
    | SYSID uid

    name - имя создаваемой роли.
    SUPERUSER / NOSUPERUSER - будет ли эта роль
    «суперпользователем», для которого нет ограничений доступа в базе данных, за
    небольшим исключением при выполнении функций с параметром права
    создавшего (в рамках этой книги не рассматривается). Статус
    суперпользователя несёт опасность и назначать его следует только в случае
    необходимости. Создать нового суперпользователя может только
    суперпользователь.

    CREATEDB / NOCREATEDB - может ли роль создавать базы данных.
        Указание CREATEDB даёт новой роли это право, а NOCREATEDB запрещает
        роли создавать базы данных.

    CREATEROLE / NOCREATEROLE - может ли роль создавать новые роли.
        Роль с правом CREATEROLE может также изменять и удалять другие роли

    INHERIT / NOINHERIT - будет ли роль «наследовать» права ролей, членом
    которых она является.

    LOGIN / NOLOGIN - разрешается ли новой роли вход на сервер; то есть,
        может ли эта роль стать начальным авторизованным именем при подключении
        клиента. Принято считать, что роль с атрибутом LOGIN соответствует
        пользователю. Роли/пользователи без этого атрибута считаются
        группами/ролями. При вызове CREATE USER - LOGIN добавится по умолчанию.
    */
    public static final String CREATE_ROLE = "CREATE ROLE %s";
}
