package com.example.fido.constants;

/*
VACUUM высвобождает пространство, занимаемое мёртвыми строками.
Соответственно, чем больше таких операций, тем больше места занимают
таблицы и индексы. Таким образом, периодически необходимо выполнять
VACUUM, особенно для часто изменяемых таблиц.
Оператор VACUUM специфичен именно для MVCC в Постгресе.
Без списка таблиц и столбцов команда VACUUM обрабатывает все
таблицы и материализованные представления в текущей базе данных, на
очистку которых текущий пользователь имеет право.
Команда VACUUM только делает пространство доступным для
повторного использования. Эта форма команды может работать параллельно с
обычными операциями чтения и записи строк таблицы, так она не требует
исключительной блокировки.

VACUUM (VERBOSE, ANALYZE) test;
*/
public enum PostgreVacuumMethods {
    /*
    выводит на экран отчёт об очистке для каждой таблицы.
     */
    VERBOSE,

    /*
    выполняет очистку (VACUUM), а затем анализ (ANALYZE) всех указанных таблиц.
    Это удобная комбинация для регулярного обслуживания БД.
    */
    ANALYZE,

    /*
    выбирает режим полной очистки, который еще и дефрагментирует
    пространство - соответственно выполняется гораздо дольше и запрашивает
    исключительную блокировку таблицы. Так как он записывает новую копию
    таблицы и не освобождает старую до завершения операции, то требует
    дополнительное место на диске.
     */
    FULL,

    /*
    указывает, что команда VACUUM пропускает все строки
    с конфликтующими блокировками.
     */
    SKIP_LOCKED,
}
