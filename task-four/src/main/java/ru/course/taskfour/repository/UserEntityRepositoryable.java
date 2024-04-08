package ru.course.taskfour.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.course.taskfour.model.UserEntity;

public interface UserEntityRepositoryable extends JpaRepository<UserEntity,Integer> {
}
