package ru.course.taskfour.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.course.taskfour.model.FileContent;
import ru.course.taskfour.model.Folder;
import ru.course.taskfour.service.FileServiceable;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class FileController {

    private final FileServiceable SERVICE;
    @PostMapping("upload")
    public List<FileContent> uploadFile(@RequestBody String folderPath) {
        return SERVICE.uploadByFolderName(folderPath);
    }
    @PostMapping("save")
    public void saveFileContent(@RequestBody String folderPath){
        SERVICE.saveFileContent(folderPath);
    };
}
