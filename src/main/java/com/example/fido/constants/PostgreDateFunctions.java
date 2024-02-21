package com.example.fido.constants;

/*
https://postgrespro.ru/docs/postgresql/9.6/functions-datetime
 */
public final class PostgreDateFunctions {
    /*
    Вычитает аргументы и выдаёт «символический» результат с годами и месяцами, а не просто днями
     */
    public final static String AGE = "age( %s )";

    /*
    Текущая дата и время (на момент начала транзакции);
    */
    public final static String NOW = "now()";

    /*
    Текущая дата и время (меняется в процессе выполнения операторов);
     */
    public final static String CLOCK_TIMESTAMP = "clock_timestamp( %s )";

    /*
    https://stackoverflow.com/questions/952493/how-do-i-convert-an-interval-into-a-number-of-hours-with-postgres
    DAY
    YEAR
    HOUR
    MONTH
    MINUTE
    SECOND
    DAY TO HOUR
    YEAR TO MONTH
    DAY TO MINUTE
    DAY TO SECOND
    HOUR TO MINUTE
    HOUR TO SECOND
    MINUTE TO SECOND
    */
    public final static String INTERVAL = "INTERVAL %s";

    /*
    The CURRENT_DATE function returns the current date in the default time zone of the database session.
    The CURRENT_DATE function returns a DATE value that represents the current date.
    Note that the CURRENT_DATE function returns the current date without any time information.
     */
    public final static String CURRENT_DATE = "CURRENT_DATE";

    /*
    Текущее время суток
     */
    public final static String CURRENT_TIME = "CURRENT_TIME";

    /*
    https://postgrespro.ru/docs/postgresql/9.6/datatype-datetime
     */
    public final static String CURRENT_TIMESTAMP = "CURRENT_TIMESTAMP";

    /*
    Преобразует время эпохи Unix (число секунд с 1970-01-01 00:00:00+00) в стандартное время
     */
    public final static String TO_TIMESTAMP = "TO_TIMESTAMP";

    /*
    Его результатом будет true, когда два периода времени (определённые своими границами) пересекаются, и false в противном случае.
    Границы периода можно задать либо в виде пары дат, времени или дат со временем,
    либо как дату, время (или дату со временем) c интервалом.
    Когда указывается пара значений, первым может быть и начало, и конец периода:
        OVERLAPS автоматически считает началом периода меньшее значение.
        Периоды времени считаются наполовину открытыми, т. е. начало<=время<конец,
        если только начало и конец не равны — в этом случае период представляет один момент времени.
        Это означает, например, что два периода, имеющие только общую границу, не будут считаться пересекающимися.
     */
    public final static String OVERLAPS = "%s OVERLAPS %s";
}
