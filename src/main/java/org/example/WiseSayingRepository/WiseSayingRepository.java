package org.example.WiseSayingRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.example.WiseSaying.WiseSaying;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

//데이터의 조회, 수정, 삭제, 생성 담당. 스캐너 출력 사용금지.
public class WiseSayingRepository {
    private final String dbDirectoryPath = "C:\\workplace\\intellij\\20241118-wise-saying\\src\\main\\java\\db\\wiseSaying\\";
    private final String dataJsonDirectoryPath = "C:\\workplace\\intellij\\20241118-wise-saying\\src\\main\\resources\\";
    private final File txtFile = new File(dataJsonDirectoryPath + "lastId.txt");
    private final JSONObject obj = new JSONObject();
    private WiseSaying wiseSaying;

    public void enrollDb(WiseSaying wiseSaying) {
        File jsonFile = new File(dbDirectoryPath + wiseSaying.getId() + ".json");

        try (FileWriter fw = new FileWriter(jsonFile)) {
            obj.put("id", wiseSaying.getId());
            obj.put("content", wiseSaying.getContent());
            obj.put("author", wiseSaying.getAuthor());
            fw.write(obj.toJSONString());
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveLastIdRepository(int i) {
        try (FileWriter fw = new FileWriter(txtFile)) {
            fw.write(String.valueOf(i));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getDataFromDb(int id) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        File jsonFile = new File(dbDirectoryPath + id + ".json");
        FileReader reader = new FileReader(jsonFile);
        Object obj = parser.parse(reader);
        JSONObject jsonObject = (JSONObject) obj;

        return jsonObject;
    }

    public void deleteDb(int input) {
        File jsonFile = new File(dbDirectoryPath + input + ".json");
        try (FileWriter fw = new FileWriter(jsonFile)) {
            obj.remove("id");
            obj.remove("content");
            obj.remove("author");
            fw.write(obj.toJSONString());
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void modifyDb(WiseSaying wiseSaying) {
        File jsonFile = new File(dbDirectoryPath + wiseSaying.getId() + ".json");

        try (FileWriter fw = new FileWriter(jsonFile)) {
            obj.put("id", wiseSaying.getId());
            obj.put("content", wiseSaying.getContent());
            obj.put("author", wiseSaying.getAuthor());
            fw.write(obj.toJSONString());
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buildJson() {
        // object mapper 객체 생성
        ObjectMapper mapper = new ObjectMapper();
        int length = getFileListFromDb().length;
        JSONParser parser = new JSONParser();
        List<WiseSaying> arr = new ArrayList<>();
        File dataFile = new File(dataJsonDirectoryPath + "data.json");

        try {
            FileWriter fw = new FileWriter(dataFile);
            for (int i = 0; i < length; i++) {
                File jsonFile = new File(dbDirectoryPath + (i + 1) + ".json");
                FileReader reader = new FileReader(jsonFile);
                Object obj = parser.parse(reader);
                JSONObject jsonObject = (JSONObject) obj;

                // json 문자열을 객체로 변환
                wiseSaying = mapper.readValue(jsonObject.toJSONString(), WiseSaying.class);
                // 객체를 arr 리스트에 추가
                arr.add(wiseSaying);
            }
            // 모든 리스트를 json 문자열로 변환
            String mergedJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(arr);
            // 작성
            fw.write(mergedJson);
            fw.flush();
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public String[] getFileListFromDb() {
        File dir = new File(dbDirectoryPath);

        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".json");
            }
        };
        String[] list = dir.list(filter);
        return list;
    }

    public String searchDb(String keyword, String keywordType) { // content, 작가
        // object mapper 객체 생성
        ObjectMapper mapper = new ObjectMapper();
        int length = getFileListFromDb().length;
        JSONParser parser = new JSONParser();
        List<WiseSaying> arr = new ArrayList<>();
        String mergedJson;
        try {
            for (int i = length; i > 0; i--) {
                File jsonFile = new File(dbDirectoryPath + i + ".json");
                FileReader reader = new FileReader(jsonFile);
                Object obj = parser.parse(reader);
                JSONObject jsonObject = (JSONObject) obj;

                if(jsonObject.get(keywordType).toString().contains(keyword)) {
                    // json 문자열을 객체로 변환
                    wiseSaying = mapper.readValue(jsonObject.toJSONString(), WiseSaying.class);
                    // 객체를 arr 리스트에 추가
                    arr.add(wiseSaying);
                }
            }
            // 모든 리스트를 json 문자열로 변환
            mergedJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(arr);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        return mergedJson;
    }
}