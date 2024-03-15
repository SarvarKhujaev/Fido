package com.example.fido.components;

import reactor.core.publisher.Mono;

/*
отвечает за валидацию различных параметров и объектов
*/
public class DataValidateInspector extends TimeInspector {
    protected <T> Mono< T > convert ( final T o ) {
        return Mono.just( o );
    }

    protected boolean objectIsNotNull (
            final Object o
    ) {
        return o != null;
    }
}
