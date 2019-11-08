package com.user.basic.authentication.services.user;

import com.user.basic.authentication.dtos.ErrorDTO;
import com.user.basic.authentication.dtos.ErrorEntry;
import com.user.basic.authentication.dtos.UserAddDTO;
import com.user.basic.authentication.entities.User;
import com.user.basic.authentication.repositories.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class UserServiceHelper {
    public static final int MIN_PHONE_NUMBER_LENGTH = 11;
    public static final int MAX_PHONE_NUMBER_LENGTH = 15;
    public static final String PHONE_NUMBER_REGEX = "^\\+[0-9]{1,3}[0-9]+$";
    private static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
    public static final String BLANK_ERROR = "blank";
    public static final String INVALID_ERROR = "invalid";

    @Autowired
    private UserRepository userRepository;

    public ErrorDTO validateUser(UserAddDTO userAddDTO) throws Exception {
        ErrorDTO errorDTO = new ErrorDTO();
        if (Objects.isNull(userAddDTO.getFirst_name()))
            errorDTO.setFirst_name(Collections.singletonList(new ErrorEntry(BLANK_ERROR)));

        if (Objects.isNull(userAddDTO.getLast_name()))
            errorDTO.setLast_name(Collections.singletonList(new ErrorEntry(BLANK_ERROR)));

        if (Objects.isNull(userAddDTO.getCountry_code()))
            errorDTO.setCountry_code(Collections.singletonList(new ErrorEntry(BLANK_ERROR)));

        validatePhoneNumber(errorDTO, userAddDTO.getPhone_number());

        if (Objects.isNull(userAddDTO.getGender()))
            errorDTO.setGender(Collections.singletonList(new ErrorEntry(BLANK_ERROR)));

        if (new Date().before(getDate(userAddDTO))) {
            errorDTO.setBirthdate(Collections.singletonList(new ErrorEntry(BLANK_ERROR)));
        }

        if (Objects.isNull(userAddDTO.getAvatar())) {
            errorDTO.setAvatar(Collections.singletonList(new ErrorEntry(BLANK_ERROR)));
        }

        if (Objects.nonNull(userAddDTO.getEmail())) validateEmail(errorDTO, userAddDTO.getEmail());

        return errorDTO;
    }

    private void validatePhoneNumber(ErrorDTO errorDTO, String phoneNumber) {
        List<ErrorEntry> phoneErrors = new ArrayList<>();

        if (Objects.nonNull(userRepository.findByPhoneNumber(phoneNumber.replace("+", ""))))
            phoneErrors.add(new ErrorEntry("taken"));

        if (!phoneNumber.matches(PHONE_NUMBER_REGEX))
            phoneErrors.add(new ErrorEntry(INVALID_ERROR));

        if (!StringUtils.isNumeric(phoneNumber.replace("+", "")))
            phoneErrors.add(new ErrorEntry("not_a_number"));

        if (phoneNumber.length() > MAX_PHONE_NUMBER_LENGTH)
            phoneErrors.add(new ErrorEntry("too_long", phoneNumber.length()));

        if (phoneNumber.length() < MIN_PHONE_NUMBER_LENGTH)
            phoneErrors.add(new ErrorEntry("too_short", phoneNumber.length()));

        if (!phoneErrors.isEmpty()) errorDTO.setPhone_number(phoneErrors);
    }

    public User getUser(UserAddDTO userAddDTO) throws Exception {
        User user = new User();
        user.setFirstName(userAddDTO.getFirst_name());
        user.setLastName(userAddDTO.getLast_name());
        user.setCountryCode(userAddDTO.getCountry_code());
        user.setPhoneNumber(userAddDTO.getPhone_number().replace("+", ""));
        user.setGender(userAddDTO.getGender());
        Date birthdate = getDate(userAddDTO);
        user.setBirthdate(birthdate);
        user.setAvatar(userAddDTO.getAvatar().getBytes());
        return user;
    }

    private Date getDate(UserAddDTO userAddDTO) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthdate = null;
        try {
            birthdate = simpleDateFormat.parse(userAddDTO.getBirthdate());
        } catch (ParseException e) {
            e.printStackTrace();
            throw new Exception("invalid date format");
        }
        return birthdate;
    }

    private void validateEmail(ErrorDTO errorDTO, String email) {
        List<ErrorEntry> emailErrors = new ArrayList<>();
        if (!email.matches(EMAIL_REGEX))
            emailErrors.add(new ErrorEntry(INVALID_ERROR));

        if (Objects.nonNull(userRepository.findByEmail(email)))
            emailErrors.add(new ErrorEntry("taken"));

        if (!emailErrors.isEmpty()) errorDTO.setEmail(emailErrors);
    }
}
