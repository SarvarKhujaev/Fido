package com.example.fido.database;

import com.example.fido.constants.PostgreSqlTables;

import org.redisson.api.RedissonReactiveClient;
import org.redisson.api.RMapReactive;
import org.redisson.config.Config;
import org.redisson.Redisson;

import reactor.core.publisher.Flux;
import java.util.UUID;

public final class RedisDataControl {
    private final RMapReactive< UUID, String > userMap;
    private final RedissonReactiveClient redissonReactiveClient;

    private static RedisDataControl redisDataControl = new RedisDataControl();

    public static RedisDataControl getInstance () {
        return redisDataControl != null ? redisDataControl : ( redisDataControl = new RedisDataControl() );
    }

    private RedisDataControl () {
        final Config config = new Config();
        config.useSingleServer()
                .setAddress( "redis://localhost:6379" )
                .setClientName( "default" )
                .setPassword( "killerbee1998" );

        this.redissonReactiveClient = Redisson.createReactive( config );

        this.userMap = this.redissonReactiveClient.getMap( PostgreSqlTables.PATRULS.name().toLowerCase() );
    }

    public void save ( final UUID uuid ) {
        this.userMap.fastPutIfAbsent( uuid, "test" ).subscribe( aBoolean -> System.out.println(
                uuid + " was saved: " + aBoolean
        ) );
    }

    public Flux< UUID > get () {
        return this.userMap.keyIterator("*");
    }

    public void close () {
        this.redissonReactiveClient.shutdown();
        redisDataControl = null;
    }
}
