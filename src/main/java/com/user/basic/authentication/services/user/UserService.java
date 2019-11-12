package com.user.basic.authentication.services.user;

import com.user.basic.authentication.dtos.UserAddDTO;
import com.user.basic.authentication.dtos.UserWebDTO;
import com.user.basic.authentication.dtos.UserWebTokenDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


public interface UserService {
    UserWebDTO addUser(UserAddDTO userAddDTO, MultipartFile avatar) throws Exception;

    UserWebTokenDTO getToken(String phoneNumber, String password);

    UserWebDTO addStatus(String phoneNumber, String token, String status);
}
