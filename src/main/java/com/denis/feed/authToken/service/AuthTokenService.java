package com.denis.feed.authToken.service;

import com.denis.feed.authToken.dao.AuthTokenRepository;
import com.denis.feed.authToken.domain.AuthToken;
import com.denis.feed.authToken.enums.TokenStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Random;

/**
 * Created by denisfeijooformoso on 06/02/16.
 */

@Service(value = "authTokenService")
public class AuthTokenService {
    protected final Logger log = LogManager.getLogger();


    @Autowired
    AuthTokenRepository authTokenRepository;

    public AuthToken create(Long idUser) {
        final AuthToken token = new AuthToken();
        token.setIdUser(idUser);
        token.setCreationDate(new Date());
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            // coding error.
            log.error("codding error ", e);
            throw new RuntimeException(e);
        }
        StringBuffer hexString = new StringBuffer();
        byte[] data = md.digest(generateRandomString(11).getBytes());
        md.update(data);
        byte[] digest = md.digest();
        for (byte b : digest) {
            hexString.append(String.format("%02x", b & 0xff));
        }
        token.setTokenHash(hexString.toString());
        token.setExpirationDate(Instant.now().plus(Duration.ofHours(1L)));
        token.setStatus(TokenStatus.ACTIVE.getStatus());
        return authTokenRepository.save(token);
    }

    public AuthToken update(AuthToken authToken) {
        return authTokenRepository.save(authToken);
    }

    public AuthToken getById(Long idToken) {
        return authTokenRepository.findByIdToken(idToken);
    }

    public AuthToken getByIdUser(Long idUser) {
        return authTokenRepository.findByIdUser(idUser);
    }

    public AuthToken getByHash(String hash) {
        return authTokenRepository.findByTokenHash(hash);
    }

    public void delete(AuthToken token) {
        authTokenRepository.delete(token);
    }

    public static String generateRandomString(int length) {
        final char[] charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        Random random = new SecureRandom();
        char[] result = new char[length];
        for (int i = 0; i < result.length; i++) {
            // picks a random index out of character set > random character
            int randomCharIndex = random.nextInt(charset.length);
            result[i] = charset[randomCharIndex];
        }
        return new String(result);
    }
}