package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.entity.UserauthEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    public UserEntity createUser(UserEntity userEntity) {
        entityManager.persist(userEntity);
        return userEntity;


    }

    public UserEntity deleteUser(final String userUuid) {
        try {
            return entityManager.createNamedQuery("userByUuid", UserEntity.class).setParameter("uuid", userUuid).getSingleResult();
        } catch (NoResultException noResultException) {
            return null;
        }
    }

    public UserEntity userProfile(final String userUuid) {
        try {
            return entityManager.createNamedQuery("userProfileByUuid", UserEntity.class).setParameter("uuid", userUuid).getSingleResult();
        } catch (NoResultException noResultException) {
            return null;
        }
    }

    public UserEntity getUserByEmail(final String email) {
        try {
            return entityManager.createNamedQuery("userByEmail", UserEntity.class)
                    .setParameter("email", email).getSingleResult();
        } catch (NoResultException noResultException) {
            return null;
        }

        }
     public UserauthEntity createAuthToken(final UserauthEntity userauthEntity)
     {
         entityManager.persist(userauthEntity);
         return userauthEntity;
     }
     public void updateUser(final UserEntity updatedUserEntity)
     {
         entityManager.merge(updatedUserEntity);
     }
     public UserauthEntity getUserAuthToken(final String accessToken) {
         try {
             return entityManager.createNamedQuery("userauthTokenByAccessToken"
                     , UserauthEntity.class).setParameter("accessToken", accessToken).getSingleResult();
         }
         catch(NoResultException noResultException)
         {
             return null;
         }
     }
     public void updateUserAuthEntity(final UserauthEntity updateduserauthEntity)
     {
         entityManager.merge(updateduserauthEntity);
     }

}