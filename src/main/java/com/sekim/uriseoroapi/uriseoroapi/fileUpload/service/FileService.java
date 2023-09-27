package com.sekim.uriseoroapi.uriseoroapi.fileUpload.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    //파일 다운로드 연습장
    public Resource downloadFile() {

        Resource resource = null;
        Path filePath = Paths.get(filePath).resolve(fileName).normalize();

        try {
            resource = new UrlResource(filePath.toUri());

            if(resource.exists()){
                return resource;
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        return resource;
    }

}
