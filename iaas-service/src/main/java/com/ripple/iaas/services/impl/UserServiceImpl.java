package com.ripple.iaas.services.impl;

import com.google.inject.Inject;
import com.ripple.iaas.application.auth.AppAuthorizer;
import com.ripple.iaas.application.auth.AuthUser;
import com.ripple.iaas.db.dao.UserDao;
import com.ripple.iaas.db.entities.User;
import com.ripple.iaas.exceptions.CustomError;
import com.ripple.iaas.exceptions.IaaSServiceException;
import com.ripple.iaas.models.UserDTO;
import com.ripple.iaas.models.enums.UserAccountType;
import com.ripple.iaas.models.request.UserRegistrationForm;
import com.ripple.iaas.services.AuthService;
import com.ripple.iaas.services.UserService;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;

import javax.ws.rs.core.Response;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author shailendra.ps
 * @since 28/07/19.
 */
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final AuthService authService;
    private final ModelMapper modelMapper;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    @Inject
    public UserServiceImpl(UserDao userDao, AuthService authService, ModelMapper modelMapper) {
        this.userDao = userDao;
        this.authService = authService;
        this.modelMapper = modelMapper;
    }

    public Optional<UserDTO> getUserByEmailId(String emailId) {
        Optional<User> userOptional = userDao.getById(emailId, null);
        return userOptional.map(user -> modelMapper.map(user, UserDTO.class));
    }

    public UserDTO registerUser(UserRegistrationForm userRegistrationForm) throws IaaSServiceException {
        try {
            validateRegisterRequest(userRegistrationForm);
            Optional<User> userOptional = userDao.getById(userRegistrationForm.getEmail(), userRegistrationForm.getMobileNo());
            if (userOptional.isPresent()) {
                CustomError error = CustomError.builder()
                        .statusType(Response.Status.CONFLICT)
                        .message("User is already registered with this email or mobile no.")
                        .build();
                throw new IaaSServiceException(error);
            }
            User user = new User();
            user.setName(userRegistrationForm.getName());
            user.setEmail(userRegistrationForm.getEmail());
            user.setMobileNo(userRegistrationForm.getMobileNo());
            user.setPassword(BCrypt.hashpw(userRegistrationForm.getPassword(), BCrypt.gensalt()));
            user.setUserAccountType(userRegistrationForm.isMaster() ? UserAccountType.MASTER : UserAccountType.NON_MASTER);
            userDao.registerUser(user);
            AppAuthorizer.accountTypeTable.put(user.getEmail(), user.getUserAccountType());
            return modelMapper.map(user, UserDTO.class);
        } catch (HibernateException e) {
            throw new IaaSServiceException(e.getMessage());
        }
    }

    public void removeUser(AuthUser authUser, String userId) throws IaaSServiceException {
        Optional<User> userOptional = userDao.getById(userId, null);
        if(!userOptional.isPresent()) {
            throw new IaaSServiceException("User is not registered");
        }
        if(UserAccountType.MASTER.name().equals(authUser.getRole()) || userOptional.get().getEmail().equals(authUser.getName())) {
            userDao.removeUser(userOptional.get());
        } else {
            throw new IaaSServiceException("User is not authorized");
        }
    }

    private void validateRegisterRequest(UserRegistrationForm userRegistrationForm) throws IaaSServiceException {
        if(StringUtils.isBlank(userRegistrationForm.getName()) ||
                StringUtils.isBlank(userRegistrationForm.getEmail()) ||
                StringUtils.isBlank(userRegistrationForm.getMobileNo()) ||
                StringUtils.isBlank(userRegistrationForm.getPassword())) {
            CustomError error = CustomError.builder()
                    .statusType(Response.Status.BAD_REQUEST)
                    .message("Value cannot be blank or Null")
                    .build();
            throw new IaaSServiceException(error);
        }

        if(!validatePhoneNumber(userRegistrationForm.getMobileNo())) {
            CustomError error = CustomError.builder()
                    .statusType(Response.Status.BAD_REQUEST)
                    .message("Mobile No. is not valid")
                    .build();
            throw new IaaSServiceException(error);
        }
        if(!validateEmail(userRegistrationForm.getEmail())) {
            CustomError error = CustomError.builder()
                    .statusType(Response.Status.BAD_REQUEST)
                    .message("Email is not valid")
                    .build();
            throw new IaaSServiceException(error);
        }
    }

    private static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

    private static boolean validatePhoneNumber(String phoneNo) {
        //validate phone numbers of format "1234567890"
        if (phoneNo.matches("\\d{10}")) return true;
            //validating phone number with -, . or spaces
        else if(phoneNo.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) return true;
            //validating phone number with extension length from 3 to 5
        else if(phoneNo.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) return true;
            //validating phone number where area code is in braces ()
        else if(phoneNo.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) return true;
            //return false if nothing matches the input
        else return false;

    }
}
