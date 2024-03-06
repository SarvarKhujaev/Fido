package com.example.fido;

import com.example.fido.database.RedisDataControl;
import com.example.fido.database.PostgreDataControl;
import com.example.fido.database.ClickHouseDataControl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FidoApplication {
    public static void main( final String[] args ) {
        SpringApplication.run( FidoApplication.class, args );
        ClickHouseDataControl.getInstance().insertValues();
        PostgreDataControl.getInstance();
        RedisDataControl.getInstance();
    }
}
