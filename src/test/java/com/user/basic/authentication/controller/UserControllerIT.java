package com.user.basic.authentication.controller;

import com.user.basic.authentication.controllers.UserController;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.beans.Transient;

public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserController userController;

    @Transactional
    public void addUser_SuccessTest(){

    }
}
