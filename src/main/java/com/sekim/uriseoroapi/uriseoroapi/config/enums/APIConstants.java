package com.sekim.uriseoroapi.uriseoroapi.config.enums;

import org.springframework.stereotype.Component;

@Component
public class APIConstants {

    public enum SEVERITY{
        BLOCKER("BLOCKER"),
        CRITICAL("CRITICAL"),
        MAJOR("MAJOR"),
        MINOR("MINOR");
        private String severity;
        SEVERITY(String key) {this.severity = severity;}
    }

}
