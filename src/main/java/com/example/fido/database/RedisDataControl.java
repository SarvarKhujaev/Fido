package com.example.fido.database;

import com.example.fido.FidoApplication;
import com.example.fido.components.LogInspector;
import com.example.fido.constants.PostgreSqlTables;

import com.example.fido.interfaces.ServiceCommonMethods;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.api.RMapReactive;
import org.redisson.config.Config;
import org.redisson.Redisson;

import reactor.core.publisher.Flux;
import java.util.UUID;

public final class RedisDataControl extends LogInspector implements ServiceCommonMethods {
    private final RMapReactive< UUID, String > userMap;
    private final RedissonReactiveClient redissonReactiveClient;

    private static RedisDataControl redisDataControl = new RedisDataControl();

    public static RedisDataControl getInstance () {
        return redisDataControl != null ? redisDataControl : ( redisDataControl = new RedisDataControl() );
    }

    private RedisDataControl () {
        final Config config = new Config();

        config.useSingleServer()
                .setAddress(
                        FidoApplication
                                .context
                                .getEnvironment()
                                .getProperty( "variables.REDIS_VARIABLES.URL" )
                ).setClientName(
                        FidoApplication
                                .context
                                .getEnvironment()
                                .getProperty( "variables.REDIS_VARIABLES.USER" )
                ).setPassword(
                        FidoApplication
                                .context
                                .getEnvironment()
                                .getProperty( "variables.REDIS_VARIABLES.PASSWORD" )
                );

        this.redissonReactiveClient = Redisson.createReactive( config );

        this.userMap = this.redissonReactiveClient.getMap( PostgreSqlTables.PATRULS.name().toLowerCase() );

        super.logging( this );
    }

    public void save ( final UUID uuid ) {
        this.userMap.fastPutIfAbsent( uuid, "test" ).subscribe();
    }

    public Flux< UUID > get () {
        return this.userMap.keyIterator( "*" );
    }

    public void close () {
        this.userMap.delete().onErrorComplete().subscribe();
        this.redissonReactiveClient.shutdown();
        redisDataControl = null;

        super.logging( this.getClass() );
    }
}
