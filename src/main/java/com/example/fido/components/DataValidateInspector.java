package com.example.fido.components;

/*
отвечает за валидацию различных параметров и объектов
*/
public class DataValidateInspector extends TimeInspector {
    protected boolean objectIsNotNull (
            final Object o
    ) {
        return o != null;
    }
}
