package com.org.retailstore.entity;

import com.org.retailstore.enums.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="USER_ID")
    private Long userId;

    @Column(name="NAME")
    private String name;

    @Column(name="EMAIL_ID", length=50)
    private String emailId;

    @Column(name="USER_TYPE", length=20)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(name="CREATED_DATE")
    private LocalDate createdDate;
}
