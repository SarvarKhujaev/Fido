package com.example.fido.constants;

/*
https://www.postgresql.org/docs/14/transaction-iso.html

https://en.wikipedia.org/wiki/Two-phase_commit_protocol

https://www.postgresql.org/docs/14/transaction-iso.html#XACT-SERIALIZABLE

В стандарте SQL есть четыре уровня изоляции транзакций75, которые
определяются в терминах аномалий76, которые допускаются при конкурентном
выполнении транзакций на этом уровне
*/
public final class PostgreTransactionIsolationTypes {
    /*
    «Грязное» чтение (Dirty read) - транзакция T1 может читать строки,
    изменённые, но ещё не зафиксированные, транзакцией T2 (не было COMMIT).
    Отмена изменений (ROLLBACK) в T2 приведёт к тому, что T1 прочитает данные,
    которых никогда не существовало
    */
    public static final String READ_UNCOMMITTED = "READ UNCOMMITTED";

    /*
    Неповторяющееся чтение (Non-repeatable read) - после того, как
    транзакция T1 прочитала строку, транзакция T2 изменила или удалила эту
    строку и зафиксировала изменения (COMMIT). При повторном чтении этой же
    строки транзакция T1 видит, что строка изменена или удалена
    */
    public static final String NON_REPEATABLE_READ = "NON REPEATABLE READ";

    /*
    В этом режиме видны только те данные, которые были зафиксированы до
    начала транзакции и предыдущие изменения в своей транзакции.
    В Постгрес этот уровень чуть усилен, не допускается даже фантомное
    чтение, за исключением аномалий сериализации.
    Другими словами, этот уровень отличается от Read Committed тем, что
    запрос в транзакции данного уровня видит снимок данных на момент начала
    первого оператора в транзакции.
    Таким образом, последовательные команды SELECT в одной транзакции
    видят одни и те же данные; они не видят изменений, внесённых и
    зафиксированных другими транзакциями после начала их текущей
    транзакции.

    Посмотрим на практике.
    Установим нужный уровень изоляции и начнём транзакции в двух сессиях.

    В первой консоли:
    SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;
    SELECT * FROM testA;

    Во второй добавим новую строку и закоммитим изменения:
    SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;
    INSERT INTO testA VALUES (777);
    COMMIT;
     */
    public static final String REPEATABLE_READ = "REPEATABLE READ";

    /*
    Фантомное чтение (Phantom read) - транзакция T1 прочитала набор
    строк по некоторому условию. Затем транзакция T2 добавила строки, также
    удовлетворяющие этому условию. Если транзакция T1 повторит запрос, она
    получит другую выборку строк
    */
    public static final String PHANTOM_READ = "PHANTOM READ";

    /*
    Аномалия сериализации (Serialization anomaly) - результат успешной
    фиксации группы транзакций оказывается несогласованным при всевозможных
    вариантах исполнения этих транзакций по очереди
    */
    public static final String SERIALIZATION_ANOMALY = "SERIALIZATION ANOMALY";
}
