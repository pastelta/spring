package ru.course.taskfour.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.course.taskfour.annotations.LogTransformation;
import ru.course.taskfour.model.FileContent;
import ru.course.taskfour.model.LoginEntity;
import ru.course.taskfour.model.UserEntity;
import ru.course.taskfour.repository.FileContentRepositoryable;
import ru.course.taskfour.repository.InMemoryFileContent;
import ru.course.taskfour.repository.LoginEntityRepositoryable;
import ru.course.taskfour.repository.UserEntityRepositoryable;
import ru.course.taskfour.service.FileServiceable;
import lombok.extern.log4j.Log4j2;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

@Service
@Log4j2
@AllArgsConstructor
@Primary
public class EntityService implements FileServiceable {

    private final InMemoryFileContent IN_MEMORY_REPOSITORY;
    private final FileContentRepositoryable REPOSITORY;

    private final UserEntityRepositoryable USER_ENTITY_REPOSITORY;
    private final LoginEntityRepositoryable LOGIN_ENTITY_REPOSITORY;

    @Override
    public List<FileContent> uploadByFolderName(String folderName) {
        return IN_MEMORY_REPOSITORY.uploadByFolderName(folderName);
    }

    @Override
    @LogTransformation
    public void saveFileContent(String folderName) {
        List<FileContent> fileContentList = IN_MEMORY_REPOSITORY.uploadByFolderName(folderName);
        fileContentList.removeIf(i->i.getAccessDate().isEmpty());
        fileContentList = REPOSITORY.saveAll(fileContentList);
        log.info("Количество записей из файла к добавлению в базу: " + REPOSITORY.count());
    }

    @Override
    @LogTransformation
    public void checkUserEntity() {
        List<FileContent> fileContentList = REPOSITORY.findAll();
        HashSet<UserEntity> userEntityList = new HashSet<>();

        for (FileContent f :
                fileContentList) {
            UserEntity userEntity = new UserEntity();

            userEntity.setUserName(f.getUserName());
            userEntity.setFullName(f.getLastName().substring(0, 1).toUpperCase() + f.getLastName().substring(1) + " " +
                    f.getFirstName().substring(0, 1).toUpperCase() + f.getFirstName().substring(1) + " " +
                    f.getMiddleName().substring(0, 1).toUpperCase() + f.getMiddleName().substring(1));

            userEntityList.add(userEntity);

        }

        USER_ENTITY_REPOSITORY.saveAll(userEntityList);
        log.info("Количество записей успешно добавленных в таблицу users: " + USER_ENTITY_REPOSITORY.count());
    }

    @Override
    @LogTransformation
    public void checkLoginEntity() {
        List<LoginEntity> loginEntityList = new ArrayList<>();
        List<UserEntity> userEntityList = USER_ENTITY_REPOSITORY.findAll();
        List<FileContent> fileContentList = REPOSITORY.findAll();

        for (FileContent f :
                fileContentList) {
            LoginEntity loginEntity = new LoginEntity();

            if (f.getApplication().equals("web") || f.getApplication().equals("mobile")) {
                loginEntity.setApplication(f.getApplication());
            } else {
                loginEntity.setApplication("other: " + f.getApplication());
            }

            int index = IntStream.range(0, userEntityList.size())
                    .filter(i -> userEntityList.get(i).getUserName().equals(f.getUserName()))
                    .findFirst()
                    .orElse(-1);

            loginEntity.setUser(userEntityList.get(index));
            loginEntity.setAccessDate(Timestamp.valueOf(f.getAccessDate()));

            loginEntityList.add(loginEntity);
        }

        LOGIN_ENTITY_REPOSITORY.saveAll(loginEntityList);
        log.info("Количество записей успешно добавленных в таблицу logins: " + LOGIN_ENTITY_REPOSITORY.count());
    }
}
