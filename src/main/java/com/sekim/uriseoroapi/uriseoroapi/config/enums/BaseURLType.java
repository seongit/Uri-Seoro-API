package com.sekim.uriseoroapi.uriseoroapi.config.enums;


import lombok.Getter;

@Getter
public enum BaseURLType {

    /* 로컬 테스트 시 사용*/
    APP_SERVER("http://localhost:8080"),
    API_SERVER("http://localhost:8081");

    private final String url;

     BaseURLType(String url) {
        this.url = url;
    }
}
