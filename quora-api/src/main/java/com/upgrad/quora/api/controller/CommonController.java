package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.UserDetailsResponse;
import com.upgrad.quora.service.business.UserCommonBusinessService;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class CommonController {

    @Autowired
    private UserCommonBusinessService userCommonBusinessService;
    @RequestMapping(method = RequestMethod.GET,path = "/userprofile/{userId}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDetailsResponse>userProfile(@PathVariable("userId")final String userUuid)throws UserNotFoundException
    {
       final UserEntity userEntity = userCommonBusinessService.userProfile(userUuid);
       UserDetailsResponse userDetailsResponse = new UserDetailsResponse().firstName(userEntity.getFirstname()).lastName(userEntity.getLastname()).userName(userEntity.getUsername()).emailAddress(userEntity.getEmail()).country(userEntity.getCountry())
               .aboutMe(userEntity.getAboutme()).dob(userEntity.getDob()).contactNumber(userEntity.getContactnumber());
       return new ResponseEntity<UserDetailsResponse>(userDetailsResponse,HttpStatus.OK);

    }
}
