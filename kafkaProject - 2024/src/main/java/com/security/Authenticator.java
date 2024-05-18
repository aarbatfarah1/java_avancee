package com.security;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Authenticator extends Remote {
    boolean authenticate(String username, String password) throws RemoteException;
}
