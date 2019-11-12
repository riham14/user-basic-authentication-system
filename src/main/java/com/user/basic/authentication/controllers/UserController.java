package com.user.basic.authentication.controllers;

import com.user.basic.authentication.dtos.UserAddDTO;
import com.user.basic.authentication.dtos.UserWebDTO;
import com.user.basic.authentication.dtos.UserWebTokenDTO;
import com.user.basic.authentication.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/add", headers = {"content-type=multipart/form-data; charset=utf-8"})
    public ResponseEntity<UserWebDTO> addUser(@RequestHeader HttpHeaders headers, @ModelAttribute("avatar") MultipartFile avatar, @ModelAttribute("userAddDTO") UserAddDTO userAddDTO) throws Exception {
        return new ResponseEntity<>(userService.addUser(userAddDTO, avatar), HttpStatus.CREATED);
    }

    @PostMapping(path = "/token")
    public ResponseEntity<UserWebTokenDTO> addUser(@RequestParam(name = "phone_number") String phoneNumber, @RequestParam(name = "password") String password) {
        return new ResponseEntity<>(userService.getToken(phoneNumber, password), HttpStatus.OK);
    }

    @PostMapping(path = "/status")
    public ResponseEntity<UserWebDTO> addUserStatus(@RequestParam(name = "phone_number") String phoneNumber, @RequestParam(name = "auth-token") String token, String status) {
        return new ResponseEntity<>(userService.addStatus(phoneNumber, token, status), HttpStatus.OK);
    }
}
