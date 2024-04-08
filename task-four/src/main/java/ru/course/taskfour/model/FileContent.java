package ru.course.taskfour.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
@Data
@Entity
@Table(name="contents")
public class FileContent implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;
    private String userName;
    private String lastName;
    private String firstName;
    private String middleName;
    private String accessDate;
    private String application;
}
