package com.teamtreehouse.instateam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import java.rmi.server.UnicastRemoteObject;

@EnableAutoConfiguration
@ComponentScan
public class ApplicationC extends UnicastRemoteObject implements ApplicationI{

    public ApplicationC() throws Exception{

    }

    @Override
    public void startSpringApplication() throws Exception {
        SpringApplication.run(ApplicationC.class);
    }
}
