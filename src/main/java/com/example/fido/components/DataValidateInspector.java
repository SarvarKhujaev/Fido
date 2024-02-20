package com.example.fido.components;

import java.util.function.Predicate;
import reactor.core.publisher.Mono;
import java.util.Objects;

/*
отвечает за валидацию различных параметров и объектов
*/
public class DataValidateInspector {
    protected <T> Mono< T > convert ( final T o ) { return Mono.just( o ); }

    protected final Predicate< Object > checkParam = Objects::nonNull;

    protected final Predicate< String > checkToken = token -> this.checkParam.test( token )
            && token.length() == 164
            && token.split( "[.]" ).length == 4
            && token.compareTo( "NzU2NWU0ODAtNjFmOC.0MzZlLTg4NDEtNjBhZDk1NzNhZj.yQEFETV8xODIxNV8wNzNfMDA3M0BBRE1fMTgyMTVfMDczXzAwNzNAM.Y5MDU0MTQ3NzM1M0B0Y1RzVHdGYmJ4Q0E1X2stbXlLQ0p3M0FldlVsZDg5WA==" ) == 0;
}
