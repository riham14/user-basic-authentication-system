package com.user.basic.authentication.services.user;

import com.user.basic.authentication.dtos.*;
import com.user.basic.authentication.entities.User;
import com.user.basic.authentication.exceptions.ErrorException;
import com.user.basic.authentication.repositories.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceHelper userServiceHelper;


    @Override
    public UserWebDTO addUser(UserAddDTO userAddDTO, MultipartFile avatar) throws Exception {
        ErrorDTO errorDTO = userServiceHelper.validateUser(userAddDTO, avatar);

        if (!errorDTO.hasEmptyLists()) {
            throw new ErrorException(HttpStatus.BAD_REQUEST, errorDTO);
        }

        User user = userServiceHelper.getUser(userAddDTO);
        user.setAvatar(avatar.getBytes());
        return UserWebDTO.from(userRepository.addUser(user));
    }

    @Override
    public UserWebTokenDTO getToken(String phoneNumber, String password) {
        User user = userRepository.findByPhoneNumber(phoneNumber);
        if (Objects.isNull(user)) {
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setPhone_number(Collections.singletonList(new ErrorEntry("No User with this phone number")));
            throw new ErrorException(HttpStatus.NOT_ACCEPTABLE, errorDTO);
        }
        user.setPassword(password);
        String token = UUID.randomUUID().toString();
        user.setToken(token);
        userRepository.save(user);
        return new UserWebTokenDTO(phoneNumber, token);
    }

    @Override
    public UserWebDTO addStatus(String phoneNumber, String token, String status) {
        User user = userRepository.findByPhoneNumber(phoneNumber);
        if(Objects.isNull(user)){
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setPhone_number(Collections.singletonList(new ErrorEntry("invalid")));
            throw new ErrorException(HttpStatus.BAD_REQUEST, errorDTO);
        }

        if(!user.getToken().equals(token)){
            ErrorDTO errorDTO = new ErrorDTO();
            errorDTO.setToken(Collections.singletonList(new ErrorEntry("invalid")));
            throw new ErrorException(HttpStatus.UNAUTHORIZED, errorDTO);
        }

        user.setStatus(status);

        return UserWebDTO.from(userRepository.save(user));
    }
}
