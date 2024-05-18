package com.security;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticatorImpl extends UnicastRemoteObject implements Authenticator {

    public AuthenticatorImpl() throws RemoteException {
        super();
    }

    @Override
    public boolean authenticate(String username, String password) throws RemoteException {
        // Assume your database URL, username, and password are hardcoded correctly for this example
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/kafka", "postgres", "mdps d postgres hh");
             PreparedStatement pst = conn.prepareStatement("SELECT password FROM patient_auth_data WHERE username = ?")) {
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String correctPassword = rs.getString("password");
                if (correctPassword.equals(password)) {
                    System.out.println("Authentication successful for user: " + username);
                    return true;
                } else {
                    System.out.println("Authentication failed for user: " + username + ": incorrect password.");
                    return false;
                }
            } else {
                System.out.println("Authentication failed for user: " + username + ": user not found.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Database connection problem: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        try {
            AuthenticatorImpl auth = new AuthenticatorImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("Authenticator", auth);
            System.out.println("Authentication server ready");
        } catch (Exception e) {
            System.out.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
