package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserCommonBusinessService {
    @Autowired
    private UserDao userDao;
    @Transactional(propagation = Propagation.REQUIRED)
    public UserEntity userProfile(final String userUuid)throws UserNotFoundException
    {
        UserEntity userEntity = userDao.userProfile(userUuid);
        if(userEntity== null){
            throw new UserNotFoundException("USR-001","User with entered uuid does not exist");
        }
        // if (userEntity.get)
        return userEntity;
    }

}
