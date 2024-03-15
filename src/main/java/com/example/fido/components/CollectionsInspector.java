package com.example.fido.components;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class CollectionsInspector extends LogInspector {
    protected CollectionsInspector () {}

    protected <T> List<T> emptyList () {
        return Collections.emptyList();
    }

    protected  <T> ArrayList<T> newList () {
        return new ArrayList<>();
    }

    protected  <T> List<T> newList ( final T ... objects ) {
        return List.of( objects );
    }

    protected <T, V> Map<T, V> newMap () {
        return new HashMap<>();
    }

    public <T> void analyze (
            final Collection<T> someList,
            final Consumer<T> someConsumer
    ) {
        someList.forEach( someConsumer );
    }

    protected <T, V> void analyze (
            final Map< T, V > someList,
            final BiConsumer<T, V> someConsumer
    ) {
        someList.forEach( someConsumer );
    }

    protected <T> boolean isCollectionNotEmpty ( final Collection<T> collection ) {
        return super.objectIsNotNull( collection ) && !collection.isEmpty();
    }

    protected <T, V> boolean isCollectionNotEmpty ( final Map<T, V> collection ) {
        return super.objectIsNotNull( collection ) && !collection.isEmpty();
    }
}
