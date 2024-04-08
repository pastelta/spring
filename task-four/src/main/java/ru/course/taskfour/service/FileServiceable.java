package ru.course.taskfour.service;

import ru.course.taskfour.model.FileContent;

import java.util.List;

public interface FileServiceable {
    List<FileContent> uploadByFolderName(String folderName);
    void saveFileContent(String folderName);
}
