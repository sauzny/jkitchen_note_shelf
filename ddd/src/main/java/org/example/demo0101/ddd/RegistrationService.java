package org.example.demo0101.ddd;

public interface RegistrationService {
    User register(Name name, PhoneNumber phoneNumber, Address address);
}
