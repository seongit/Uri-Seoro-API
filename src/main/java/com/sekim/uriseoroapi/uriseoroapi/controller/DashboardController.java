package com.sekim.uriseoroapi.uriseoroapi.controller;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;

@RestController
@CrossOrigin("*")
@RequestMapping("/pipeline/api/")
public class DashboardController {


    /**
     *  [Test] data parsing
     * 설치형 대시보드 수집 요청에 대한 응답값 처리 구간
     * se.kim
     */

    @PostMapping("/ansible/dashboard")
    public String ansibleDashboardGet(@RequestBody String result){

        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(result);

            JSONObject jsonObj = (JSONObject) obj;

            //System.out.println(jsonObj.get("mdType"));
            //System.out.println(jsonObj.get("result"));

            String tmpStr = (String) jsonObj.get("result");
            String newStr = tmpStr.replaceAll("\\s+"," "); // 연속된 공백을 하나로 변경
            String[] list = newStr.split(" "); // 공백을 기준으로 배열에 담음

            // vo 생성 예정
            int cpu_current_usage = 0;
            int memory_total = 0;
            int memory_current_usage = 0;
            int disk_total = 0;
            int dist_current_usage = 0;

            for(int i=0; i < list.length; i++){
                // CPU
                if(list[i].equals(">>")){
//                    System.out.println("Available CPU = " + list[i+1]);
//                    System.out.println("Used CPU = " + (100 - Double.parseDouble(list[i+1])));
                    cpu_current_usage = (int) (100 - Double.parseDouble(list[i+1]));
                }

                // Memory
                if(list[i].equals("Mem:")){
//                    System.out.println("Total Memory = " + list[i+1]);
//                    System.out.println("Used Memory = " + list[i+2]);
                    memory_total = Integer.parseInt(list[i+1]);
                    memory_current_usage = Integer.parseInt(list[i+2]);
                }

                // Disk
                if(list[i].equals("/dev/vda3")){
//                    System.out.println("Total Disk = " + list[i+2]);
//                    System.out.println("Used Disk = " + list[i+3]);
                    disk_total = Integer.parseInt(list[i+2]);
                    dist_current_usage = Integer.parseInt(list[i+3]);
                }
            }

            System.out.println( "-------------------------START-------------------------" + "\n"
                                +"cpu_current_usage : " + cpu_current_usage + "\n"
                                + "memory_total : " + memory_total + "\n"
                                + "memory_current_usage : " + memory_current_usage + "\n"
                                + "disk_total : " + disk_total + "\n"
                                + "dist_current_usage : " + dist_current_usage + "\n"
                                + "------------------------END----------------------------");


        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

}
