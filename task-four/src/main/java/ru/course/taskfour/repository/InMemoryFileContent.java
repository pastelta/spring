package ru.course.taskfour.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import ru.course.taskfour.model.FileContent;
import ru.course.taskfour.model.Folder;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
@Log4j2
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
                    content = new String(Files.readAllBytes(file.toPath()));
                    List<String> rows = Arrays.asList(content.split(";"));

                    for (int i = 0; i < rows.size(); i++) {
                        List<String> items = Arrays.asList(rows.get(i).split(","));
                        FileContent fc = new FileContent();
                        if (items.get(4).isEmpty()){
                            log.info("Отсутствует Дата входа, данные не будут внесены в базу: " + items
                                    + "; путь к файлу: " + file);
                        }
                        fc.setUserName(items.get(0));   //Логин
                        fc.setLastName(items.get(1));   //Фамилия
                        fc.setFirstName(items.get(2));  //Имя
                        fc.setMiddleName(items.get(3)); //Отчество
                        fc.setAccessDate(items.get(4)); //Дата входа
                        fc.setApplication(items.get(5));//Тип приложения

                        lst.add(fc);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return lst;
    }
}
