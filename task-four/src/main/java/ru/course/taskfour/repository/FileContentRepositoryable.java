package ru.course.taskfour.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.course.taskfour.model.FileContent;

public interface FileContentRepositoryable extends JpaRepository<FileContent, Integer> {
}
