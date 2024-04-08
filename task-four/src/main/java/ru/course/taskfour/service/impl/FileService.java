package ru.course.taskfour.service.impl;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import ru.course.taskfour.model.FileContent;
import ru.course.taskfour.repository.InMemoryFileContent;
import ru.course.taskfour.service.FileServiceable;

import java.util.List;

@Service
@AllArgsConstructor
public class FileService implements FileServiceable {

    private final InMemoryFileContent IN_MEMORY_REPOSITORY;

    @Override
    public List<FileContent> uploadByFolderName(String folderName) {
        return IN_MEMORY_REPOSITORY.uploadByFolderName(folderName);
    }

    @Override
    public void saveFileContent(String folderName) {}

    @Override
    public void checkUserEntity() {}

    @Override
    public void checkLoginEntity() {}
}
