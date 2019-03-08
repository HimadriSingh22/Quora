package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.entity.UserauthEntity;
import com.upgrad.quora.service.exception.AuthenticationFailedException;
import com.upgrad.quora.service.exception.SignOutRestrictedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
public class UserSignUpBusinessService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordCryptographyProvider cryptographyProvider;
     //**** SignUp Method ****//
    @Transactional(propagation = Propagation.REQUIRED)
    public UserEntity signup(UserEntity userEntity) {
        String[] encryptedText = cryptographyProvider.encrypt(userEntity.getPassword());
        userEntity.setSalt(encryptedText[0]);
        userEntity.setPassword(encryptedText[1]);
       // if(userEntity.getEmail().equals())
        return userDao.createUser(userEntity);
    }

    // **** SignIn Method****//
    @Transactional(propagation = Propagation.REQUIRED)
    public UserauthEntity authenticate(final String username , final String password) throws AuthenticationFailedException
    {
       UserEntity userEntity=userDao.getUserByEmail(username);
       if(userEntity==null)
       {
           throw new AuthenticationFailedException("ATH-001","This username does not exist");
       }

       final String encryptedPassword = cryptographyProvider.encrypt(password,userEntity.getSalt());
       if(encryptedPassword.equals(userEntity.getPassword()))
       {
           JwtTokenProvider jwtTokenProvider= new JwtTokenProvider(encryptedPassword);
           UserauthEntity userauthEntity =new UserauthEntity();
           userauthEntity.setUser(userEntity);

           final ZonedDateTime now = ZonedDateTime.now();
           final ZonedDateTime expiresAt = now.plusHours(8);
           userauthEntity.setAccessToken(jwtTokenProvider.generateToken(userEntity.getUuid(),now,expiresAt));
           userauthEntity.setLoginAt(now);
           userauthEntity.setExpiresAt(expiresAt);

           userDao.createAuthToken(userauthEntity);
           userDao.updateUser(userEntity);
           return userauthEntity;
       }
       else
           {
        throw new AuthenticationFailedException("ATH-002","Password failed");
       }

    }

    //**** SignOut Method****//

    @Transactional(propagation = Propagation.REQUIRED)
    public UserauthEntity signout(final String accessToken)throws SignOutRestrictedException
    {
        UserauthEntity userauthEntity=userDao.getUserAuthToken(accessToken);
        if(userauthEntity==null)
        {
            throw new SignOutRestrictedException("SGR-001","User is not signed in");
        }
        final ZonedDateTime now = ZonedDateTime.now();
        userauthEntity.setLogoutAt(now);
        userDao.updateUserAuthEntity(userauthEntity);

        return userauthEntity;
    }
}
