package org.example.demo0101.old;

public interface RegistrationService {
    User register(String name, String phone, String address) throws ValidationException;
}
