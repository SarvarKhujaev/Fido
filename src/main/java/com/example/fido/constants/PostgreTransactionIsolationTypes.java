package com.example.fido.constants;

/*
https://www.postgresql.org/docs/14/transaction-iso.html

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
    public static final String DIRTY_READ = "DIRTY READ";

    /*
    Неповторяющееся чтение (Non-repeatable read) - после того, как
    транзакция T1 прочитала строку, транзакция T2 изменила или удалила эту
    строку и зафиксировала изменения (COMMIT). При повторном чтении этой же
    строки транзакция T1 видит, что строка изменена или удалена
    */
    public static final String NON_REPEATABLE_READ = "NON REPEATABLE READ";

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
