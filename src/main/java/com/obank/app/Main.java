package com.obank.app;

import com.obank.app.repositories.UserRepository;
import com.obank.app.repositories.implementations.UserRepositoryJSON;
import com.obank.app.screens.ActionsScreen;

public class Main {
    private static UserRepository userRepository = new UserRepositoryJSON();
    public static void main(String[] args) {
        ActionsScreen actionsScreen = new ActionsScreen();
        actionsScreen.setVisible(true);
    }
}