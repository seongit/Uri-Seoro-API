package com.sekim.uriseoroapi.uriseoroapi.dto;

import com.sekim.uriseoroapi.uriseoroapi.model.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto {

    Map<String, JSONArray> membership;

    int projectId;

    // 구성원을 다중 선택할 수 있기 때문에 반환 타입은 List<Member>
    public List<Member> toEntity(){

        List <Member> res = new ArrayList<>();
        Member member = null;

        for(int i=0; i<membership.get("user_ids").size(); i++){
            // System.out.println("use_ids ==== > " + membership.get("user_ids").get(i));
            member =  Member.builder()
                    .userId((Integer) membership.get("user_ids").get(i))
                    .projectId(projectId)
                    .build();
            res.add(member);
        }

        return res;
    }

}
