package com.teamtreehouse.instateam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import java.rmi.Naming;

public class Server {
    public static void main(String[] args) throws Exception{
//        SpringApplication.run(Server.class, args);
        ApplicationC obj = new ApplicationC();
        Naming.rebind("StartSpringApplication", obj);
    }
}
