package com.sekim.uriseoroapi.uriseoroapi.config.enums;

import lombok.Getter;

@Getter
public enum AdminAuth {

    // HTTP Basic Authorization 사용하였으며, 전역적으로 사용하기 위해 Enum으로 선언
    BASIC_BASE_64("Basic YWRtaW46QFNlb25nMTAyZXVu"),
    SONAR_BASIC_BASE_64("Basic YWRtaW46b2tlc3RybzIwMTg=");
    private final String key;
    AdminAuth(String key){
        this.key = key;
    }

}
