package com.sekim.uriseoroapi.uriseoroapi.config.enums;


import lombok.Getter;

@Getter
public enum FileUpload {

    UPLOAD_PATH("/Users/seongeun/Desktop/Test/");

    private final String path;

    FileUpload(String path) {this.path = path;}


}
