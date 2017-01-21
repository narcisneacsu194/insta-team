package com.teamtreehouse.instateam;

import java.rmi.Naming;

public class Client {
    public static void main(String[] args) throws Exception{
        ApplicationI obj = (ApplicationI) Naming.lookup("StartSpringApplication");
        obj.startSpringApplication();
    }
}
