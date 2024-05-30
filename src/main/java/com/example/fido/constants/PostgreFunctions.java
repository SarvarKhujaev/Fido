package com.example.fido.constants;

public final class PostgreFunctions {
    /*
    https://www.postgresqltutorial.com/postgresql-tutorial/postgresql-any/

    SELECT *
    FROM employees
    WHERE salary < ANY (
        SELECT salary
        FROM employees
    );
     */
    public final static String ANY = "ANY( %s )";

    /*
    https://www.postgresqltutorial.com/postgresql-tutorial/postgresql-cast/
     */
    public final static String CAST = "CAST( %s, AS %s )";

    /*
    Текущие размеры значения массива можно получить с помощью функции array_dims
    array_dims выдаёт результат типа text, что удобно скорее для людей, чем для программ.
    Размеры массива также можно получить с помощью функций array_upper и array_lower,
    которые возвращают соответственно верхнюю и нижнюю границу для указанной размерности
     */
    public final static String ARRAY_DIMS = "array_dims( %s )";

    public final static String ARRAY_UPPER = "array_upper( %s, 1 )";

    public final static String ARRAY_LOWER = "array_lower( %s, 1 )";

    public final static String ARRAY_LENGTH = "array_length( %s, 1 )";

    /*
    UPDATE test
    SET temp = array_cat( temp, ARRAY[10, 11] )
    WHERE name = 1
    RETURNING *;
    */
    public final static String ARRAY_CAT = "array_cat( %s, ARRAY[ %s ] )";

    /*
    UPDATE test
    SET temp = array_append( temp, 13 )
    WHERE name = 1
    RETURNING *;
    */
    public final static String ARRAY_APPEND = "array_append( %s, %s )";

    /*
    UPDATE test
    SET temp = array_prepend( 13, temp )
    WHERE name = 1
    RETURNING *;
    */
    public final static String ARRAY_PREPEND = "array_prepend( %s, ARRAY[ %s ] )";

    /*
    Вы также можете искать определённые значения в массиве, используя функции array_position и array_positions.
    Первая функция возвращает позицию первого вхождения значения в массив, а вторая — массив позиций всех его вхождений.

    Например:
        SELECT array_position(ARRAY['sun','mon','tue','wed','thu','fri','sat'], 'mon');
     */
    public final static String ARRAY_POSITION = "array_position( ARRAY[ %s ], %s )";

    /*
    SELECT array_positions(ARRAY[1, 4, 3, 1, 3, 4, 2, 1], 1);
     */
    public final static String ARRAY_POSITIONS = "array_positions( ARRAY[ %s ], %s )";

    /*
    возвращает общее число элементов массива по всем измерениям. Фактически это число строк, которое вернёт функция unnest
    */
    public final static String CARDINALITY = "cardinality ( %s )";

    /*
    А так можно найти в таблице строки, в которых массивы содержат только значения, равные 10000:
        SELECT * FROM sal_emp WHERE 10000 = ALL (pay_by_quarter);
     */
    public final static String ALL = "ANY( %s )";

    /*
    Число символов в строке
    */
    public final static String CHAR_LENGTH = "char_length( %s )";

    public final static String UUID_GENERATOR = "uuid_generate_v4()";

    /*
    https://blog.devgenius.io/using-generate-series-in-postgres-5920cacfbd3e <- docs

    Generate_series() is a built-in function that makes it easy to create ordered tables of numbers or date.
    This function takes three arguments.
    Two of which (start and stop) are required, the third (step) is optional when making a sequence of numbers
    but required for a sequence of dates.

    start: indicates the beginning of series.

    stop: this is the value the series shouldn’t go beyond.

    step: the interval between each item in the series. When it is not specified, one (1) is the default.

    Example:
        SELECT GENERATE_SERIES(1,10) AS ratings;
     */
    public final static String GENERATE_SERIES = "generate_series( %s )";

    /*
    https://www.sqliz.com/postgresql-ref/json_to_recordset/ <- docs

    The PostgreSQL json_to_recordset() function expands the specified top-level
    JSON array (its elements are objects) into a set of rows with the type defined in the AS clause.

    SELECT *
    FROM
      json_to_recordset(
        '[{"x": "A", "y": 1}, {"x": "B", "y": 2}]'
      ) AS x(x TEXT, y INT);
     */
    public final static String JSON_TO_RECORDSET = "json_to_recordset( %s ) AS %s";
}
