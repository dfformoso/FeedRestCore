package com.denis.feed.user.dao;

import org.springframework.data.repository.Repository;
import com.denis.feed.user.domain.User;

/**
 * Created by denis on 24/09/15
 */
public interface UserRepository extends Repository<User, Long> {

    User getByIdUser(Long idUser);

    User getByUsername(String username);

    User save(User user);

}
