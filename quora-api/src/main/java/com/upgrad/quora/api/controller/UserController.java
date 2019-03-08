package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.SigninResponse;
import com.upgrad.quora.api.model.SignoutResponse;
import com.upgrad.quora.api.model.SignupUserRequest;
import com.upgrad.quora.api.model.SignupUserResponse;
import com.upgrad.quora.service.business.UserSignUpBusinessService;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.entity.UserauthEntity;
import com.upgrad.quora.service.exception.AuthenticationFailedException;
import com.upgrad.quora.service.exception.SignOutRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class UserController {
    @Autowired
    private UserSignUpBusinessService userSignUpBusinessService;
    @RequestMapping(method = RequestMethod.POST,path = "user/signup", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SignupUserResponse> signup(final SignupUserRequest signupUserRequest)
    {
        final UserEntity userEntity = new UserEntity();
        userEntity.setUuid(UUID.randomUUID().toString());
        userEntity.setFirstname(signupUserRequest.getFirstName());
        userEntity.setLastname(signupUserRequest.getLastName());
        userEntity.setUsername(signupUserRequest.getUserName());
        userEntity.setEmail(signupUserRequest.getEmailAddress());
        userEntity.setPassword(signupUserRequest.getPassword());
        userEntity.setCountry(signupUserRequest.getCountry());
        userEntity.setAboutme(signupUserRequest.getAboutMe());
        userEntity.setDob(signupUserRequest.getDob());
        userEntity.setContactnumber(signupUserRequest.getContactNumber());
        userEntity.setRole("notadmin");
        final  UserEntity createdUserEntity = userSignUpBusinessService.signup(userEntity);
        SignupUserResponse userResponse =new SignupUserResponse().id(createdUserEntity.getUuid()).status("User Successfully Registered");
        return new ResponseEntity<SignupUserResponse>(userResponse,HttpStatus.CREATED);
    }
    @RequestMapping(method = RequestMethod.POST,path = "/user/signin",produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SigninResponse> signin(@RequestHeader("authorization") final String authorization)throws AuthenticationFailedException
    {
        byte[] decode = Base64.getDecoder().decode(authorization.split("Basic")[1]);
        String decodedText = new String(decode);
        String[] decodedArray = decodedText.split(":");
        UserauthEntity userauthEntity= userSignUpBusinessService.authenticate(decodedArray[0],decodedArray[1]);
        UserEntity user = userauthEntity.getUser();
        SigninResponse signinResponse = new SigninResponse().id(user.getUuid()).message("SIGNED IN SUCCESSFULLY");

        HttpHeaders headers =new HttpHeaders();
        headers.add("access-token",userauthEntity.getAccessToken());
        return new ResponseEntity<SigninResponse>(signinResponse,headers,HttpStatus.OK);
    }

@RequestMapping(method = RequestMethod.POST,path ="/user/signout",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SignoutResponse>signout(@RequestHeader("authorization") final String authorization)throws SignOutRestrictedException
   {

     UserauthEntity userauthEntity = userSignUpBusinessService.signout(authorization);
     UserEntity user = userauthEntity.getUser();
     SignoutResponse signoutResponse = new SignoutResponse().id(user.getUuid()).message("SIGNED OUT SUCCESSFULLY");
     return new ResponseEntity<SignoutResponse>(signoutResponse,HttpStatus.OK);
   }

}
