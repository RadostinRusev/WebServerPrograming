package com.example.demo.user;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "users")
@Entity
@Data
@NoArgsConstructor
public class User {
    @Column
    @Id
    private Long user_id;
    @Column(length = 20 ,unique = true)
    private String name;
    @Column
    private String password;
    @Column(length= 200,unique = true)
    private String email;
}
