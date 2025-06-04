package com.obank.app.repositories;

import com.obank.app.models.User;

public interface UserRepository {
    User findByEmail(String email);
}
