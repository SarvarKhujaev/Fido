package com.example.fido.constants;

public final class PostgreCommands {
    public final static String SELECT_ROWS_FOR_BANKNOTE_NAME = "SELECT * FROM "
            + PostgreSqlTables.BANKNOTE
            + " WHERE name = ?;";

    public final static String SELECT_COUNT = "SELECT count(*) FROM "
            + PostgreSqlTables.BANKNOTE
            + ";";

    public final static String SELECT_ALL_BANKNOTE_WITH_PAGINATION =
            "SELECT * FROM "
                    + PostgreSqlTables.BANKNOTE
                    + " ORDER BY updated_date ASC" // сортируем по дате сохранения
                    + " LIMIT ?"
                    + " OFFSET ?"
                    + ";";

    /*
    The HAVING clause was added to SQL because the WHERE clause cannot be used with aggregate functions.
    Aggregate functions are often used with GROUP BY clauses,
    and by adding HAVING we can write condition like we do with WHERE clauses.

    Важно понимать, как соотносятся агрегатные функции и SQL-предложения WHERE и HAVING.
    Основное отличие WHERE от HAVING заключается в том, что WHERE сначала выбирает строки,
    а затем группирует их и вычисляет агрегатные функции (таким образом, она отбирает строки для вычисления агрегатов),
    тогда как HAVING отбирает строки групп после группировки и вычисления агрегатных функций.
    Как следствие, предложение WHERE не должно содержать агрегатных функций; не имеет смысла использовать агрегатные функции для определения строк для вычисления агрегатных функций.
    Предложение HAVING, напротив, всегда содержит агрегатные функции.
    (Строго говоря, вы можете написать предложение HAVING, не используя агрегаты, но это редко бывает полезно.
    То же самое условие может работать более эффективно на стадии WHERE.)

    Example:
        SELECT COUNT(customer_id), country
        FROM customers
        GROUP BY country
        HAVING COUNT(customer_id) > 5;
     */
    public final static String HAVING = "HAVING %s;";

    public final static String DELETE = "DELETE FROM %s WHERE %s;";

    public final static String SELECT = "SELECT * FROM";

    public final static String INSERT = "INSERT INTO %s.%s ( %s ) VALUES ";

    /*
    https://postgrespro.ru/docs/postgresql/9.6/sql-set-transaction

    https://www.postgresqltutorial.com/postgresql-tutorial/postgresql-transaction/

    BEGIN TRANSACTION ISOLATION LEVEL SERIALIZABLE;
    */
    public final static String BEGIN_TRANSACTION = "BEGIN TRANSACTION;";

    public final static String COMMIT_TRANSACTION = "COMMIT TRANSACTION;";

    public final static String SET_TRANSACTION = "SET TRANSACTION;";

    /*
    Иногда бывает полезно получать данные из модифицируемых строк в процессе их обработки.
    Это возможно с использованием предложения RETURNING, которое можно задать для команд INSERT, UPDATE и DELETE.
    Применение RETURNING позволяет обойтись без дополнительного запроса к базе для сбора данных и это особенно ценно,
    когда как-то иначе трудно получить изменённые строки надёжным образом.

    В предложении RETURNING допускается то же содержимое, что и в выходном списке команды SELECT (см. Раздел 7.3).
    Оно может содержать имена столбцов целевой таблицы команды или значения выражений с этими столбцами.
    Также часто применяется краткая запись RETURNING *, выбирающая все столбцы целевой таблицы по порядку.

    В команде INSERT данные, выдаваемые в RETURNING, образуются из строки в том виде, в каком она была вставлена.
    Это не очень полезно при простом добавлении, так как в результате будут получены те же данные, что были переданы клиентом.
    Но это может быть очень удобно при использовании вычисляемых значений по умолчанию.
    Например, если в таблице есть столбец serial, в котором генерируются уникальные идентификаторы,
    команда RETURNING может возвратить идентификатор, назначенный новой строке:

        CREATE TABLE users (firstname text, lastname text, id serial primary key);

        INSERT INTO users (firstname, lastname) VALUES ('Joe', 'Cool') RETURNING id;

    Предложение RETURNING также очень полезно с INSERT ... SELECT.

    В команде UPDATE данные, выдаваемые в RETURNING, образуются новым содержимым изменённой строки. Например:
        UPDATE products SET price = price * 1.10
        WHERE price <= 99.99
        RETURNING name, price AS new_price;

    В команде DELETE данные, выдаваемые в RETURNING, образуются содержимым удалённой строки. Например:
    DELETE FROM products
      WHERE obsoletion_date = 'today'
      RETURNING *;
    */
    public final static String RETURNING = "RETURNING %s;";

    public final static String UPDATE = "UPDATE";

    /*
    http://www.postgresql.org/docs/8.1/interactive/functions-datetime.html#FUNCTIONS-DATETIME-EXTRACT
    */
    public final static String EXTRACT = "EXTRACT %s";

    public final static String GROUP_BY = "GROUP BY %s";

    /*
    В каждом внутреннем списке GROUPING SETS могут задаваться ноль или более столбцов или выражений,
    которые воспринимаются так же, как если бы они были непосредственно записаны в предложении GROUP BY.
    Пустой набор группировки означает, что все строки сводятся к одной группе
    (которая выводится, даже если входных строк нет), как описано выше для агрегатных функций без предложения GROUP BY.

    https://postgrespro.ru/docs/postgresql/9.6/queries-table-expressions
    */
    public final static String GROUPING_SETS = "GROUPING SETS (%s)";

    /*
    https://postgrespro.ru/docs/postgresql/9.6/queries-table-expressions
    */
    public final static String ROLLUP = "ROLLUP (%s)";

    /*
    https://postgrespro.ru/docs/postgresql/9.6/queries-table-expressions
    */
    public final static String CUBE = "CUBE (%s)";

    public final static String ORDER_BY = "ORDER BY (%s)";

    /*
    The PARTITION BY clause within OVER divides the rows into groups, or partitions,
    that share the same values of the PARTITION BY expression(s).
    For each row, the window function is computed across the rows that fall into the same partition as the current row.
    */
    public final static String PARTITION_BY = "PARTITION BY (%s)";

    /*
    После обработки списка выборки в результирующей таблице можно дополнительно исключить дублирующиеся строки.
    Для этого сразу после SELECT добавляется ключевое слово DISTINCT
    */
    public final static String DISTINCT = "DISTINCT";

    /*
    https://postgrespro.ru/docs/postgresql/9.6/functions-conditional <- docs

    Выражение CASE в SQL представляет собой общее условное выражение, напоминающее операторы if/else в других языках программирования:

    CASE WHEN условие THEN результат
         [WHEN ...]
         [ELSE результат]
    END

    SELECT a,
       CASE WHEN a=1 THEN 'one'
            WHEN a=2 THEN 'two'
            ELSE 'other'
       END
    FROM test;

    SELECT a,
       CASE a WHEN 1 THEN 'one'
              WHEN 2 THEN 'two'
              ELSE 'other'
       END
    FROM test;

    SELECT ... WHERE CASE WHEN x <> 0 THEN y/x > 1.5 ELSE false END;SELECT *
    FROM test
    WHERE CASE status
            WHEN 'attached'
            THEN age > 20

            WHEN 'created'
            THEN age > 0

            WHEN 'arrived'
            THEN age > 40

            ELSE false
            END;
     */
    public final static String CASE = "CASE %s";

    /*
    NULLIF(значение1, значение2)

    Функция NULLIF выдаёт значение NULL, если значение1 равно значение2;
    в противном случае она возвращает значение1. Это может быть полезно для реализации обратной операции к COALESCE.

    В частности, для примера, показанного выше:
        SELECT NULLIF(value, '(none)')

    В данном примере если value равно (none), выдаётся null, а иначе возвращается значение value.

    Два её аргумента должны быть сравнимых типов. Если говорить точнее, они сравниваются точно так же,
    как сравнивались бы в записи значение1 = значение2, так что для этих типов должен существовать подходящий оператор =.

    Результат будет иметь тот же тип, что и первый аргумент, но есть одна тонкость.
    Эта функция фактически возвращает первый аргумент подразумеваемого оператора =,
    который в некоторых случаях преобразуется к типу второго аргумента.
    Например, NULLIF(1, 2.2) возвращает numeric, так как оператор integer = numeric не существует, существует только оператор numeric = numeric.
     */
    public final static String NULLIF = "NULLIF %s";

    /*
    Функция COALESCE возвращает первый попавшийся аргумент, отличный от NULL.
    Если же все аргументы равны NULL, результатом тоже будет NULL.
    Это часто используется при отображении данных для подстановки некоторого значения по умолчанию вместо значений NULL:

    SELECT COALESCE(description, short_description, '(none)')
    Этот запрос вернёт значение description, если оно не равно NULL,
    либо short_description, если оно не NULL, и строку (none), если оба эти значения равны NULL.

    Аргументы должны быть приводимыми к одному общему типу, который и будет типом результата (подробнее об этом говорится в Разделе 10.5).

    Как и выражение CASE, COALESCE вычисляет только те аргументы, которые необходимы для получения результата; то есть,
    аргументы правее первого отличного от NULL аргумента не вычисляются.
    Эта функция соответствует стандарту SQL, а в некоторых других СУБД её аналоги называются NVL и IFNULL.
    */
    public final static String COALESCE = "COALESCE %s";

    /*
    Функции GREATEST и LEAST выбирают наибольшее или наименьшее значение из списка выражений.
    Все эти выражения должны приводиться к общему типу данных, который станет типом результата (подробнее об этом в Разделе 10.5).
    Значения NULL в этом списке игнорируются, так что результат выражения будет равен NULL, только если все его аргументы равны NULL.

    Заметьте, что функции GREATEST и LEAST не описаны в стандарте SQL, но часто реализуются в СУБД как расширения.
    В некоторых других СУБД они могут возвращать NULL, когда не все, а любой из аргументов равен NULL.
     */
    public final static String GREATEST = "GREATEST %s";

    public final static String LEAST = "LEAST %s";

    /*
    Аргументом EXISTS является обычный оператор SELECT, т. е. подзапрос.
    Выполнив запрос, система проверяет, возвращает ли он строки в результате.
    Если он возвращает минимум одну строку, результатом EXISTS будет «true», а если не возвращает ни одной — «false».

    Подзапрос может обращаться к переменным внешнего запроса, которые в рамках одного вычисления подзапроса считаются константами.

    Вообще говоря, подзапрос может выполняться не полностью, а завершаться, как только будет возвращена хотя бы одна строка.
    Поэтому в подзапросах следует избегать побочных эффектов (например, обращений к генераторам последовательностей);
    проявление побочного эффекта может быть непредсказуемым.

    Так как результат этого выражения зависит только от того, возвращаются строки или нет, но не от их содержимого,
    список выходных значений подзапроса обычно не имеет значения. Как следствие, широко распространена практика,
    когда проверки EXISTS записываются в форме EXISTS(SELECT 1 WHERE ...). Однако из этого правила есть и исключения,
    например с подзапросами с предложением INTERSECT.

    Этот простой пример похож на внутреннее соединение по столбцу col2,
    но он выдаёт максимум одну строку для каждой строки в tab1, даже если в tab2 ей соответствуют несколько строк:

    SELECT col1
    FROM tab1
    WHERE EXISTS (SELECT 1 FROM tab2 WHERE col2 = tab1.col2);
    */
    public final static String EXISTS = "EXISTS( %s )";

    /*
    В правой части конструкции в скобках записывается подзапрос, который должен возвращать ровно один столбец.
    Вычисленное значение левого выражения сравнивается со значением в каждой строке результата подзапроса
    с помощью заданного оператора условия, который должен выдавать логическое значение.
    Результатом ANY будет «true», если хотя бы для одной строки условие истинно,
    и «false» в противном случае (в том числе, и когда подзапрос не возвращает строк).

    Ключевое слово SOME является синонимом ANY. Конструкцию IN можно также записать как = ANY.

    Заметьте, что если условие не выполняется ни для одной из строк, а хотя бы для одной строки условный оператор выдаёт NULL,
    конструкция ANY возвращает NULL, а не false. Это соответствует принятым в SQL правилам сравнения переменных со значениями NULL.

    Так же, как и с EXISTS, здесь не следует рассчитывать на то, что подзапрос будет всегда выполняться полностью.
     */
    public final static String ANY = "ANY( %s )";

    /*
    В правой части конструкции в скобках записывается подзапрос, который должен возвращать ровно один столбец.
    Вычисленное значение левого выражения сравнивается со значением в каждой строке результата подзапроса
    с помощью заданного оператора условия, который должен выдавать логическое значение.
    Результатом ALL будет «true», если условие истинно для всех строк (и когда подзапрос не возвращает строк),
    или «false», если находятся строки, для которых оно ложно. Результатом выражения будет NULL,
    если ни для одной из строк подзапроса результат сравнения не равен true, а минимум для одной равен NULL.

    Конструкция NOT IN равнозначна <> ALL.

    Так же, как и с EXISTS, здесь не следует рассчитывать на то, что подзапрос будет всегда выполняться полностью.

    конструктор_строки оператор ALL (подзапрос)

    В левой части этой формы ALL записывается конструктор строки (подробнее они описываются в Подразделе 4.2.13).
    Справа в скобках записывается подзапрос, который должен возвращать ровно столько столбцов, сколько содержит строка в выражении слева.
    Вычисленные значения левого выражения сравниваются построчно со значениями во всех строках,
    возвращённых подзапросом, с применением заданного оператора.
    Результатом всего выражения ALL будет «true», если для всех строк подзапроса результатом сравнения будет true
    (или если подзапрос не возвращает строк), либо «false», если результат сравнения равен false для любой из строк подзапроса.
    Результатом выражения будет NULL, если ни для одной из строк подзапроса результат сравнения не равен true, а минимум для одной равен NULL.
     */
    public final static String ALL = "ALL( %s )";

    public final static String SOME = "SOME( %s )";
}
