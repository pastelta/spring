package ru.course.taskfour.model;


import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name="logins")
public class LoginEntity implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;
    private Timestamp accessDate;
    @ManyToOne (optional=false, cascade=CascadeType.ALL)
    @JoinColumn(name="userId")
    private UserEntity user;
    private String application;
}
