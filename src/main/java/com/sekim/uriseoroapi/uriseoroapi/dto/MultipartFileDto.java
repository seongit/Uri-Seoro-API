package com.sekim.uriseoroapi.uriseoroapi.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MultipartFileDto {
    MultipartFile file;

}
