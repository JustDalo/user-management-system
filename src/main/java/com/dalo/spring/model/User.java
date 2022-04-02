package com.dalo.spring.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id")
    private Long id;
    @Column(name = "usr_first_name")
    public String firstName;
    @Column(name = "usr_last_name")
    public String lastName;
    @Column(name = "usr_middle_name")
    public String middleName;
    @Column(name = "usr_sex")
    public String sex;
    @Column(name = "usr_phone_number")
    public String phoneNumber;
    @Column(name = "usr_email")
    public String email;
    @ManyToOne
    @JoinColumn(name="usr_country_id", referencedColumnName = "ctr_id")
    public Country country;

    public User() {
    }

    public User(Long id, String firstName, String lastName, String middleName, String sex, String phoneNumber, String email, Country country) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.sex = sex;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.country = country;
    }
}
