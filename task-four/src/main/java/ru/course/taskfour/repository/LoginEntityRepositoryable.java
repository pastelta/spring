package ru.course.taskfour.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.course.taskfour.model.LoginEntity;

public interface LoginEntityRepositoryable extends JpaRepository<LoginEntity,Integer> {
}
