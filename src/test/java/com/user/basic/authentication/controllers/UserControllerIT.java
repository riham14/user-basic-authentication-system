package com.user.basic.authentication.controllers;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.user.basic.UserBasicAuthenticationSystemApplication;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserBasicAuthenticationSystemApplication.class)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@ComponentScan(basePackages = {"com.user.basic.authentication"})
@TestExecutionListeners(mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS, value = {
        DbUnitTestExecutionListener.class})
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    public void addUser_SuccessTest() throws Exception {
        MockMultipartFile avatar = new MockMultipartFile(
                "avatar",
                "test_avatar.png",
                "image/png", "test_avatar.png".getBytes());

        ResultActions result = this.mockMvc.perform(
                multipart("/user/add")
                        .file(avatar)
                        .param("first_name", "first")
                        .param("last_name", "last")
                        .param("country_code", "EG")
                        .param("phone_number", "+2010123456789")
                        .param("birthdate", "1990-10-20")
                        .param("gender", "MALE"))
                .andDo(print());

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.first_name", is("first")));
    }

    @Test
    @Transactional
    public void addUser_NullFirstName_FailTest() throws Exception {
        MockMultipartFile avatar = new MockMultipartFile(
                "avatar",
                "test_avatar.png",
                "image/png", "test_avatar.png".getBytes());

        ResultActions result = this.mockMvc.perform(
                multipart("/user/add")
                        .file(avatar)
                        .param("last_name", "last")
                        .param("country_code", "EG")
                        .param("phone_number", "+2010123456789")
                        .param("birthdate", "1990-10-20")
                        .param("gender", "MALE"))
                .andDo(print());

        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.errors.first_name", hasSize(greaterThan(0))));
        result.andExpect(jsonPath("$.errors.first_name[0].error", is("blank")));
    }

    @Test
    @Transactional
    public void addUser_NullAvatar_FailTest() throws Exception {

        ResultActions result = this.mockMvc.perform(
                multipart("/user/add")
                        .param("last_name", "last")
                        .param("country_code", "EG")
                        .param("phone_number", "+2010123456789")
                        .param("birthdate", "1990-10-20")
                        .param("gender", "MALE"))
                .andDo(print());

        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.errors.avatar", hasSize(greaterThan(0))));
        result.andExpect(jsonPath("$.errors.avatar[0].error", is("blank")));
    }

    @Test
    @Transactional
    public void addUser_InvalidPhoneNumber_FailTest() throws Exception {
        MockMultipartFile avatar = new MockMultipartFile(
                "avatar",
                "test_avatar.png",
                "image/png", "test_avatar.png".getBytes());

        ResultActions result = this.mockMvc.perform(
                multipart("/user/add")
                        .file(avatar)
                        .param("first_name", "first")
                        .param("last_name", "last")
                        .param("country_code", "EG")
                        .param("phone_number", "2010123456789")
                        .param("birthdate", "1990-10-20")
                        .param("gender", "MALE"))
                .andDo(print());

        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.errors.phone_number", hasSize(greaterThan(0))));
        result.andExpect(jsonPath("$.errors.phone_number[0].error", is("invalid")));
    }

    @Test
    @Transactional
    @DatabaseSetup(value = "/db/AddUser_TakenPhoneNumber.xml", type = DatabaseOperation.INSERT)
    public void addUser_TakenPhoneNumber_FailTest() throws Exception {
        MockMultipartFile avatar = new MockMultipartFile(
                "avatar",
                "test_avatar.png",
                "image/png", "test_avatar.png".getBytes());

        ResultActions result = this.mockMvc.perform(
                multipart("/user/add")
                        .file(avatar)
                        .param("first_name", "first")
                        .param("last_name", "last")
                        .param("country_code", "EG")
                        .param("phone_number", "+2010123456789")
                        .param("birthdate", "1990-10-20")
                        .param("gender", "MALE"))
                .andDo(print());

        result.andExpect(status().isBadRequest());
        result.andExpect(jsonPath("$.errors.phone_number", hasSize(greaterThan(0))));
        result.andExpect(jsonPath("$.errors.phone_number[0].error", is("taken")));
    }

    @Test
    @Transactional
    @DatabaseSetup(value = "/db/GetAuthToken_SuccessTest.xml", type = DatabaseOperation.INSERT)
    public void getAuthToken_SuccessTest() throws Exception {
        ResultActions result = this.mockMvc.perform(
                post("/user/token")
                        .param("phone_number", "2010123456789")
                        .param("password", "P@ssw0rd"))
                .andDo(print());

        result.andExpect(status().isOk());
    }

    @Test
    @Transactional
    @DatabaseSetup(value = "/db/GetAuthToken_NonExistingPhone_FailTest.xml", type = DatabaseOperation.INSERT)
    public void getAuthToken_NonExistingPhone_FailTest() throws Exception {
        ResultActions result = this.mockMvc.perform(
                post("/user/token")
                        .param("phone_number", "2010123987789")
                        .param("password", "P@ssw0rd"))
                .andDo(print());

        result.andExpect(status().isNotAcceptable());
    }
}
