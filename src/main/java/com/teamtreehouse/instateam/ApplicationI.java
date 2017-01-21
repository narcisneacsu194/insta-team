package com.teamtreehouse.instateam;

import java.rmi.Remote;

public interface ApplicationI extends Remote{
    void startSpringApplication() throws Exception;
}
