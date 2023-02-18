package com.sekim.uriseoroapi.uriseoroapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.simple.JSONArray;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrioritiesDto {
    JSONArray issue_priorities;

}
