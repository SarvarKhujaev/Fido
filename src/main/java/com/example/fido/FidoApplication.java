package com.example.fido;

import com.example.fido.database.RedisDataControl;
import com.example.fido.database.PostgreDataControl;
import com.example.fido.database.ClickHouseDataControl;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FidoApplication {
    public static ApplicationContext context;

    public static void main( final String[] args ) {
        context = SpringApplication.run( FidoApplication.class, args );
        ClickHouseDataControl.getInstance().close();
        PostgreDataControl.getInstance().close();
        RedisDataControl.getInstance().close();
    }
}
