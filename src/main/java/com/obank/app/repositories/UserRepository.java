package com.obank.app.repositories;

import com.obank.app.models.User;

public interface UserRepository {
    User findByEmail(String email);
    void updateUser(User user);
    void createUser(User user);
    boolean checkIfUserExists(String email);
}
