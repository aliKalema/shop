package com.phenomenal.shop.service.upload;

import com.phenomenal.shop.configuration.UserStorageProperty;
import com.phenomenal.shop.repository.UserImageRepository;
import com.phenomenal.shop.utils.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;

@Service
public class UserFileServiceImpl implements FileService{
    private final Path docStorageLocation;
    @Autowired
    UserImageRepository uir;

    @Autowired
    public UserFileServiceImpl(UserStorageProperty documentStorageProperty){
        this.docStorageLocation = Paths.get("src/main/resources/static/"+documentStorageProperty.getUploadDirectory()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.docStorageLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String addFile(MultipartFile multipartfile) throws NoSuchAlgorithmException {
        return create(multipartfile);
    }
    public String create(MultipartFile multipartFile) throws NoSuchAlgorithmException {
        byte[] array = new byte[5];
        new Random().nextBytes(array);
        String generated = SystemUtils.getSaltString();
        String extension = com.google.common.io.Files.getFileExtension(multipartFile.getOriginalFilename());
        String fileName = new StringBuilder().append(generated).append(new Date().getTime())
                .append(String.valueOf(multipartFile.getSize())).append(".").append(extension).toString();
        this.storeFile(multipartFile,fileName);
        return fileName;
    }
    private void storeFile(MultipartFile file, String name){
        Path targetLocation = this.docStorageLocation.resolve(name);
        try {
            Files.copy(file.getInputStream(), targetLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}