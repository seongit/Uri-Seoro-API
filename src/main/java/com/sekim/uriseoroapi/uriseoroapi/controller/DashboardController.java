package com.sekim.uriseoroapi.uriseoroapi.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/pipeline/api/")
public class DashboardController {


    /**
     *  [Test] data parsing
     * 설치형 대시보드 수집 요청에 대한 응답값 처리 구간
     * se.kim
     */

    // 백업
//    @PostMapping("/ansible/dashboard")
//    public String ansibleDashboardGet(@RequestBody String result){
//
//        JSONParser parser = new JSONParser();
//
//        try {
//            Object obj = parser.parse(result);
//
//            JSONObject jsonObj = (JSONObject) obj;
//
//            //System.out.println(jsonObj.get("mdType"));
//            //System.out.println(jsonObj.get("result"));
//
//            String tmpResutStr = (String) jsonObj.get("result");
//            String newStr = tmpResutStr.replaceAll("\\s+"," "); // 연속된 공백을 하나로 변경
//            String[] list = newStr.split(" "); // 공백을 기준으로 배열에 담음
//
//            // vo 생성 예정
//            int cpu_current_usage = 0;
//            int memory_total = 0;
//            int memory_current_usage = 0;
//            int disk_total = 0;
//            int dist_current_usage = 0;
//
//            // todo 예외 처리 필요
//            // 1. 서버가 죽었을 경우
//            // 2. 서비스가 죽었을 경우
//
//            for(int i=0; i < list.length; i++){
//                // CPU
//                if(list[i].equals(">>")){
////                    System.out.println("Available CPU = " + list[i+1]);
////                    System.out.println("Used CPU = " + (100 - Double.parseDouble(list[i+1])));
//                    cpu_current_usage = (int) (100 - Double.parseDouble(list[i+1]));
//                }
//
//                // Memory
//                if(list[i].equals("Mem:")){
////                    System.out.println("Total Memory = " + list[i+1]);
////                    System.out.println("Used Memory = " + list[i+2]);
//                    memory_total = Integer.parseInt(list[i+1]);
//                    memory_current_usage = Integer.parseInt(list[i+2]);
//                }
//
//                // Disk
//                //디스크 볼륨 경로를 어떻게 설정하냐에 따라 다를 수 있기 때문에 i == 17인 경우만 담음
//                if(i == 17){
//                    disk_total = Integer.parseInt(list[i]);
//                    dist_current_usage = Integer.parseInt(list[i+1]);
//                }
//
//                // todo 서비스 active 상태가 false일 경우 예외 처리하는 로직 개발 필요
//                // Service
//
//            }
//
//            System.out.println( "-------------------------START-------------------------" + "\n"
//                                +"cpu_current_usage : " + cpu_current_usage + "\n"
//                                + "memory_total : " + memory_total + "\n"
//                                + "memory_current_usage : " + memory_current_usage + "\n"
//                                + "disk_total : " + disk_total + "\n"
//                                + "dist_current_usage : " + dist_current_usage + "\n"
//                                + "------------------------END----------------------------");
//
//
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//
//        return result;
//    }


    // 테스트 중
    public Map<String,String> jsonToMap(String json) throws Exception{

        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<Map<String, String>> typeReference = new TypeReference<Map<String,String>>() {};

        return objectMapper.readValue(json, typeReference);
    }


    @PostMapping("/ansible/dashboard")
    public void ansibleDashboardGet(@RequestBody String resData){

        JSONParser parser = new JSONParser();

        try {
            if(resData != null && !resData.equals("false")){

                JSONObject obj = (JSONObject) parser.parse(resData); // jsonObj로 파싱
                String req = obj.toString();

                Map<String,String> test = jsonToMap(req);
                Iterator <String> keys = test.keySet().iterator();

                while (keys.hasNext()){
                    JSONObject tmpJsonObj = new JSONObject();
                    String key = (String) keys.next();

                    // key 값이 harbor, jenkins등으로 다르기 때문에 다음과 같이 구현하였음
                    String tmpResultStr = (String) test.get(key);
                    String result = tmpResultStr.replaceAll("\\s+"," "); // 연속된 공백을 하나로 변경
                    String [] list = result.split(" ");

                    int cpu_current_usage = 0;
                    int memory_total = 0;
                    int memory_current_usage = 0;
                    int disk_total = 0;
                    int dist_current_usage = 0;

                    for(int i=0; i<list.length; i++){

//                        System.out.println(list[i]);
                        if(list[i].equals(">>")){
//                            System.out.println("Available CPU = " + list[i+1]);
//                            System.out.println("Used CPU = " + (100 - Double.parseDouble(list[i+1])));
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
                        //디스크 볼륨 경로를 어떻게 설정하냐에 따라 다를 수 있기 때문에 i == 17인 경우만 담음
                        if(i == 17){
                            disk_total = Integer.parseInt(list[i]);
                            dist_current_usage = Integer.parseInt(list[i+1]);
                        }

                    }

                    System.out.println( "-------------------------START-------------------------" + "\n"
                            +"cpu_current_usage : " + cpu_current_usage + "\n"
                            + "memory_total : " + memory_total + "\n"
                            + "memory_current_usage : " + memory_current_usage + "\n"
                            + "disk_total : " + disk_total + "\n"
                            + "dist_current_usage : " + dist_current_usage + "\n"
                            + "------------------------END----------------------------");
                }

            }else if(resData.equals("false")){
                System.out.println("실패");
            }

        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
