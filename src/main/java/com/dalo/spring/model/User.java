package com.dalo.spring.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import java.sql.Blob;

@Data
@Entity
@Table(name = "user")
@Builder
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id")
    private Long id;
    @NotBlank
    @Column(name = "usr_first_name")
    private  String firstName;
    @NotBlank
    @Column(name = "usr_last_name")
    private  String lastName;
    @Column(name = "usr_middle_name")
    private  String middleName;
    @NotBlank
    @Column(name = "usr_sex")
    private  String sex;
    @NotBlank
    @Column(name = "usr_phone_number")
    private  String phoneNumber;
    @NotBlank
    @Column(name = "usr_email")
    private  String email;
    @Lob
    @Column(name = "usr_image")
    private  byte[] image;
    @ManyToOne
    @JoinColumn(name="usr_country_id", referencedColumnName = "ctr_id")
    private  Country country;
}
