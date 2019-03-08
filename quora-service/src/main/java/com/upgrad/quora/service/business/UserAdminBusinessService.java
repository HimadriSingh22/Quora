package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.entity.UserauthEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserAdminBusinessService
{

    @Autowired
    private UserDao userDao;
    @Transactional(propagation = Propagation.REQUIRED)
    public UserEntity deleteUser(final String userUuid,final String authorizationToken ) throws UserNotFoundException,AuthorizationFailedException {

        UserauthEntity userauthEntity = userDao.getUserAuthToken(authorizationToken);
        String role = userauthEntity.getUser().getRole();

        if(userauthEntity==null){throw new AuthorizationFailedException("ATHR-001","User has not signed in");}
        if (role.equalsIgnoreCase("notadmin")) { throw new AuthorizationFailedException("ATHR-003","Unauthorised Access,Entered user is not an admin");}


            UserEntity userEntity = userDao.deleteUser(userUuid);
            if (userEntity == null) {
                throw new UserNotFoundException("USR-001", "User with entered uuid to be deleted does not exist");
            }
            return userEntity;
        }


}

