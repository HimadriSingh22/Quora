package com.upgrad.quora.service.entity;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="users",schema = "quora")
@NamedQueries({
        @NamedQuery(name="userByUuid", query = "delete  from UserEntity u  where u.uuid= :uuid"),
        @NamedQuery(name="userProfileByUuid",query = "SELECT u from UserEntity u where u.uuid= :uuid"),
        @NamedQuery(name="userByEmail", query = "SELECT u from UserEntity u where u.email= :email")
})


public class UserEntity implements Serializable {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="uuid")
    @NotNull
    private String  uuid;

    @Column(name = "firstname")
    @NotNull
    @Size(max = 200)
    private String firstname;

    @Column(name="lastname")
    @NotNull
    @Size(max=200)
    private String lastname;

    @Column(name = "username")
    @NotNull
    private String username;

    @Column(name = "email")
    @NotNull
    @Size(max=200)
    private String email;


    @Column(name="password")
    @NotNull
    @Size(max=200)
    private String password;

    @Column(name="salt")
    @NotNull
    private String salt;

    @Column(name="country")
    @NotNull
    @Size(max=200)
    private String country;

    @Column(name="aboutme")
    @NotNull
    @Size(max=300)
    private String aboutme;

    @Column(name="dob")
    @NotNull
    private String  dob;

    @Column(name="role")
    @Size(max=200)
    private String role;

    @Column(name = "contact number")
    @NotNull
    @Size(max=10)
    private String contactnumber;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAboutme() {
        return aboutme;
    }

    public void setAboutme(String aboutme) {
        this.aboutme = aboutme;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContactnumber() {
        return contactnumber;
    }

    public void setContactnumber(String contactnumber) {
        this.contactnumber = contactnumber;
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this).hashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
