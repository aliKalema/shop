package com.phenomenal.shop.service.upload;

import org.springframework.web.multipart.MultipartFile;

import java.security.NoSuchAlgorithmException;

public interface FileService {
    public String addFile(MultipartFile multipartfile) throws NoSuchAlgorithmException;

}

