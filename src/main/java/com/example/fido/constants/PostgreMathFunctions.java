package com.example.fido.constants;

/*
https://postgrespro.ru/docs/postgresql/9.6/functions-math
 */
public final class PostgreMathFunctions {
    /*
    модуль числа (абсолютное значение)
     */
    public final static String ABS = "abs( %f )";

    /*
    остаток от деления y/x
    */
    public final static String MOD = "mod( %f, %f )";

    /*
    	случайное число в диапазоне 0.0 <= x < 1.0
    */
    public final static String RANDOM = "random()";

    /*
    кубический корень
     */
    public final static String CBRT = "cbrt( %f )";

    public final static String FACTORIAL = "factorial( %f )";
}
