package ru.course.taskfour.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.course.taskfour.model.FileContent;
import ru.course.taskfour.repository.FIleContentRepositoryable;
import ru.course.taskfour.repository.InMemoryFileContent;
import ru.course.taskfour.service.FileServiceable;

import java.util.List;

@Service
@AllArgsConstructor
@Primary
public class EntityService implements FileServiceable {

    private final InMemoryFileContent IN_MEMORY_REPOSITORY;
    private final FIleContentRepositoryable REPOSITORY;

    @Override
    public List<FileContent> uploadByFolderName(String folderName) {
        return IN_MEMORY_REPOSITORY.uploadByFolderName(folderName);
    }

    @Override
    public void saveFileContent(String folderName) {
        List<FileContent> fileContentList = IN_MEMORY_REPOSITORY.uploadByFolderName(folderName);
        fileContentList = REPOSITORY.saveAll(fileContentList);
    }
}
