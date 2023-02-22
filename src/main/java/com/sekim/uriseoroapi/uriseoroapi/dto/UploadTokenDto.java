package com.sekim.uriseoroapi.uriseoroapi.dto;

import com.sekim.uriseoroapi.uriseoroapi.model.Role;
import com.sekim.uriseoroapi.uriseoroapi.model.User;
import lombok.*;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
   public class UploadTokenDto {

   private int id;
   private String token;

}
