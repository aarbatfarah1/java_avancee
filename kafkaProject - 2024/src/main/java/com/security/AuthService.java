package com.security;

public interface AuthService {
    boolean authenticate(String username, String password);
    String generateAuthToken(String username);
}
