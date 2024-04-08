package ru.course.taskfour.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
