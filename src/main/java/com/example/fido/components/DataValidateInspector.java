package com.example.fido.components;

import java.util.function.Predicate;
import reactor.core.publisher.Mono;
import java.util.Objects;

/*
отвечает за валидацию различных параметров и объектов
*/
public class DataValidateInspector {
    protected <T> Mono< T > convert ( final T o ) {
        return Mono.just( o );
    }

    protected final Predicate< Object > checkParam = Objects::nonNull;
}
