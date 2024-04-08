package ru.course.taskfour.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import ru.course.taskfour.model.FileContent;
import ru.course.taskfour.model.Folder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class InMemoryFileContent {
    public List<FileContent> uploadByFolderName(String folderPath) {
        Folder f;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            f = objectMapper.readValue(folderPath, Folder.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String content = "";
        File folder = new File(f.getFolderName());
        File[] listOfFiles = folder.listFiles();
        List<FileContent> lst = new ArrayList<>();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                try {
                    content += new String(Files.readAllBytes(file.toPath()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        List<String> rows = Arrays.asList(content.split(";"));
        for (int i = 0; i < rows.size(); i++) {
            List<String> items = Arrays.asList(rows.get(i).split(" "));
            FileContent fc = new FileContent();
            fc.setUserName(items.get(0));
            fc.setLastName(items.get(1));
            fc.setFirstName(items.get(2));
            fc.setMiddleName(items.get(3));
            fc.setAccessDate(items.get(4));
            fc.setApplication(items.get(5));

            lst.add(fc);
        }
        return lst;
    }
}
