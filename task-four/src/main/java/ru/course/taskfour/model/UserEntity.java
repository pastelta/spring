package ru.course.taskfour.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name="users")
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;
    private String userName;
    private String fullName;
    @OneToMany (mappedBy="id", fetch=FetchType.EAGER)
    private List<LoginEntity> loginEntityList;
}
