package com.example.fido;

import com.example.fido.database.ClickHouseDataControl;
import com.example.fido.database.PostgreDataControl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FidoApplication {
    public static void main( String[] args ) {
//        SpringApplication.run( FidoApplication.class, args );
//        PostgreDataControl.getInstance();
        new ClickHouseDataControl();
    }
}
