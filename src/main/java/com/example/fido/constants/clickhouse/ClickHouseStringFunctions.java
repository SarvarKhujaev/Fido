package com.example.fido.constants.clickhouse;

public final class ClickHouseStringFunctions {
    /*
    Возвращает количество вхождений подстроки.

    Для поиска без учета регистра, используйте функции
    countSubstringsCaseInsensitive или
    countSubstringsCaseInsensitiveUTF8
     */
    public static final String COUNT_SUBSTRINGS = "countSubstrings";

    /*
    Возвращает 1 если needle является подпоследовательностью haystack, иначе 0.
     */
    public static final String HAS_SUBSEQUENCE = "hasSubsequence";

    /*
    replaceAll(haystack, pattern, replacement)
    Замена всех вхождений подстроки pattern в haystack на подстроку replacement.
     */
    public static final String REPLACE_ALL = "replaceAll";

    /*
    Возвращает 1, если строка начинается указанным префиксом, в противном случае 0.
    */
    public static final String STARTS_WITH = "startsWith";

    /*
    translate(s, from, to)
    Данная функция заменяет символы в строке ‘s’ в соответствии с поэлементным отображением определяемым строками
    ‘from’ и ‘to’. ‘from’ и ‘to’ должны быть корректными константными ASCII строками одного размера.
    Не ASCII символы в оригинальной строке не изменяются.
     */
    public static final String TRANSLATE = "translate";

    /*
        Возвращает 1, если строка завершается указанным суффиксом, и 0 в противном случае.
    */
    public static final String ENDS_WITH = "endsWith";
}
