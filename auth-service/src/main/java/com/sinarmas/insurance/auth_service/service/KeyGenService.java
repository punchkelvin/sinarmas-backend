package com.sinarmas.insurance.auth_service.service;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class KeyGenService {

    public static String getBase64Key() {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        return Encoders.BASE64.encode(key.getEncoded());
    }


}
