package com.security;


import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AuthClient {
    public static void main(String[] args) {
        try {
            System.out.println("Locating registry...");
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            System.out.println("Looking up Authenticator...");
            Authenticator stub = (Authenticator) registry.lookup("Authenticator");

            System.out.println("Attempting to authenticate...");
            boolean result = stub.authenticate("bF6YOlfX", "AF7u99wk");
            System.out.println("Authentication result: " + result);
        } catch (Exception e) {
            System.out.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
