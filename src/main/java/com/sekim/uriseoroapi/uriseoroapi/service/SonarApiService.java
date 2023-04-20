package com.sekim.uriseoroapi.uriseoroapi.service;

import org.json.simple.JSONObject;

public interface SonarApiService {
    JSONObject getAllRules(String page);

}
