package com.project.diploma.service.services.implementation;

import com.project.diploma.service.services.HashingService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class HashingServiceImpl implements HashingService {

    private final BCryptPasswordEncoder encoder;

    public HashingServiceImpl(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public String hashPassword(String password) {
        return encoder.encode(password);
    }
}
