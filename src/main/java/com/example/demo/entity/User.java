package com.example.demo.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "userregister")
public class User {
    @Id
    private String username;

    private String password;

    private String cpassword;

    private String phone;

    private String email;

    @UpdateTimestamp
    @Column(name = "timestamp")
    private LocalDateTime lastUpdated;



}
