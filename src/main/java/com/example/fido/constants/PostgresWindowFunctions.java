package com.example.fido.constants;

/*
https://www.postgresqltutorial.com/postgresql-window-function/

Note that a window function always performs the calculation on the result set after the
JOIN, WHERE, GROUP BY and HAVING clause and before the final ORDER BY clause in the evaluation order.

window_function(arg1, arg2,..) OVER (
   [PARTITION BY partition_expression]
   [ORDER BY sort_expression [ASC | DESC] [NULLS {FIRST | LAST }])
*/
public final class PostgresWindowFunctions {
    public final static String OVER = "OVER( %s )";

    /*
    SELECT
       wf1() OVER w,
       wf2() OVER w,
    FROM table_name
    WINDOW w AS (PARTITION BY c1 ORDER BY c2);
    */
    public final static String WINDOW = "WINDOW( %s )";

    /*
    PARTITION BY clause

    The PARTITION BY clause divides rows into multiple groups or partitions to which the window function is applied.
    Like the example above, we used the product group to divide the products into groups (or partitions).

    The PARTITION BY clause is optional. If you skip the PARTITION BY clause, the window function will treat the whole result set as a single partition.
    */
    public final static String PARTITION_BY = "PARTITION BY %s";



    /*
    The ROW_NUMBER(), RANK(), and DENSE_RANK() functions assign an integer to each row based on its order in its result set.
    */

    /*
    Rank the current row within its partition with gaps.

    The RANK() function assigns ranking within an ordered partition. If rows have the same values, the  RANK() function assigns the same rank, with the next ranking(s) skipped.

    See the following query:

    SELECT
        product_name,
        group_name,
      price,
        RANK () OVER (
            PARTITION BY group_name
            ORDER BY
                price
        )
    FROM
        products
    INNER JOIN product_groups USING (group_id);
    */
    public final static String RANK = "RANK %s";

    /*
    Number the current row within its partition starting from 1.

    The ROW_NUMBER() function assigns a sequential number to each row in each partition. See the following query:

    SELECT
        product_name,
        group_name,
        price,
        ROW_NUMBER () OVER (
            PARTITION BY group_name
            ORDER BY
                price
        )
    FROM
        products
    INNER JOIN product_groups USING (group_id);
    */
    public final static String ROW_NUMBER = "ROW_NUMBER %s";

    /*
    Rank the current row within its partition without gaps.

    Similar to the RANK() function, the DENSE_RANK() function assigns a rank to each row within an ordered partition, but the ranks have no gap. In other words, the same ranks are assigned to multiple rows and no ranks are skipped.

    SELECT
        product_name,
        group_name,
        price,
        DENSE_RANK () OVER (
            PARTITION BY group_name
            ORDER BY
                price
        )
    FROM
        products
    INNER JOIN product_groups USING (group_id);
    */
    public final static String DENSE_RANK = "DENSE_RANK %s";


    /*
    The FIRST_VALUE and LAST_VALUE functions
    */

    /*
    Return a value evaluated against the last row within its partition.

    The following statement uses the LAST_VALUE() function to return the highest price for every product group.

    SELECT
        product_name,
        group_name,
        price,
        LAST_VALUE (price) OVER (
            PARTITION BY group_name
            ORDER BY
                price RANGE BETWEEN UNBOUNDED PRECEDING
            AND UNBOUNDED FOLLOWING
        ) AS highest_price_per_group
    FROM
        products
    INNER JOIN product_groups USING (group_id);
    */
    public final static String LAST_VALUE = "FIRST_VALUE %s";

    /*
    Return a value evaluated against the first row within its partition.

    The FIRST_VALUE() function returns a value evaluated against the first row within its partition, whereas the LAST_VALUE() function returns a value evaluated against the last row in its partition.

    The following statement uses the FIRST_VALUE() to return the lowest price for every product group.

    SELECT
        product_name,
        group_name,
        price,
        FIRST_VALUE (price) OVER (
            PARTITION BY group_name
            ORDER BY
                price
        ) AS lowest_price_per_group
    FROM
        products
    INNER JOIN product_groups USING (group_id);
    */
    public final static String FIRST_VALUE = "FIRST_VALUE %s";


    /*
    The LAG and LEAD functions

    The LAG() function has the ability to access data from the previous row, while the LEAD() function can access data from the next row.

    Both LAG() and LEAD() functions have the same syntax as follows:

    LAG  (expression [,offset] [,default]) over_clause;
    LEAD (expression [,offset] [,default]) over_clause;

    In this syntax:

     expression – a column or expression to compute the returned value.
     offset – the number of rows preceding ( LAG)/ following ( LEAD) the current row. It defaults to 1.
     default – the default returned value if the offset goes beyond the scope of the window. The default is NULL if you skip it.
    */

    /*
    Return a value evaluated at the row that is at a specified physical offset row before the current row within the partition.

    The following statement uses the LAG() function to return the prices from the previous row and calculates the difference between the price of the current row and the previous row.

    SELECT
        product_name,
        group_name,
        price,
        LAG (price, 1) OVER (
            PARTITION BY group_name
            ORDER BY
                price
        ) AS prev_price,
        price - LAG (price, 1) OVER (
            PARTITION BY group_name
            ORDER BY
                price
        ) AS cur_prev_diff
    FROM
        products
    INNER JOIN product_groups USING (group_id);
    */
    public final static String LAG = "LAG %s";

    /*
    Return a value evaluated at the row that is offset rows after the current row within the partition.

    The following statement uses the LEAD() function to return the prices from the next row and calculates the difference between the price of the current row and the next row.

    SELECT
        product_name,
        group_name,
        price,
        LEAD (price, 1) OVER (
            PARTITION BY group_name
            ORDER BY
                price
        ) AS next_price,
        price - LEAD (price, 1) OVER (
            PARTITION BY group_name
            ORDER BY
                price
        ) AS cur_next_diff
    FROM
        products
    INNER JOIN product_groups USING (group_id);
    */
    public final static String LEAD = "LEAD %s";



    /*
    Divide rows in a partition as equally as possible and assign each row an integer starting from 1 to the argument value.
    */
    public final static String NTILE = "NTILE %s";

    /*
    Return a value evaluated against the nth row in an ordered partition.
    */
    public final static String NTH_VALUE = "NTH_VALUE %s";

    /*
    The ORDER BY clause uses the NULLS FIRST or NULLS LAST option to specify whether nullable values should be first or last in the result set.
    The default is NULLS LAST option
    */
    public final static String ORDER_BY = "ORDER BY %s";

    /*
    Return the relative rank of the current row.
    */
    public final static String CUME_DIST = "CUME_DIST %s";

    /*
    Return the relative rank of the current row (rank-1) / (total rows – 1)
    */
    public final static String PERCENT_RANK = "PERCENT_RANK	%s";
}
