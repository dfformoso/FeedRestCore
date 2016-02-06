package com.denis.feed.authToken.dao;

import com.denis.feed.authToken.domain.AuthToken;
import org.springframework.data.repository.Repository;

/**
 * Created by denisfeijooformoso on 06/02/16.
 */
public interface AuthTokenRepository extends Repository<AuthToken, Long> {

    AuthToken save(AuthToken token);

    AuthToken findByIdToken(Long idToken);

    AuthToken findByIdUser(Long idUser);

    AuthToken findByTokenHash(String tokenHash);

    void delete(AuthToken token);
}
