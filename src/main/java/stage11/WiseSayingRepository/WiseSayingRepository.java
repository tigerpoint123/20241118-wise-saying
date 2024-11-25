package stage11.WiseSayingRepository;

import com.google.gson.JsonArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import stage11.WiseSaying.WiseSaying;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

//데이터의 조회, 수정, 삭제, 생성 담당. 스캐너 출력 사용금지.
public class WiseSayingRepository {
    private final String directoryPath = "C:\\workplace\\intellij\\20241118-wise-saying\\src\\main\\java\\db\\wiseSaying\\";
    private final File txtFile = new File(directoryPath + "lastId.txt");
    private final JSONObject obj = new JSONObject();

    public void saveDb(WiseSaying list, int i) {
        File jsonFile = new File(directoryPath + (i + 1) + ".json");

        try (FileWriter fw = new FileWriter(jsonFile)) {
            obj.put("id", list.id.get(i));
            obj.put("content", list.content.get(i));
            obj.put("author", list.author.get(i));
            fw.write(obj.toJSONString());
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveLastId(WiseSaying list) {
        try (FileWriter fw = new FileWriter(txtFile)) {
            fw.write(String.valueOf(list.id.size()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getDataFromDb(int id) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        File jsonFile = new File(directoryPath + id + ".json");
        FileReader reader = new FileReader(jsonFile);
        Object obj = parser.parse(reader);
        JSONObject jsonObject = (JSONObject) obj;

        return jsonObject;
    }

    public int getLastId() throws IOException {
        FileReader reader = new FileReader(txtFile);
        int id = (char) reader.read();
        return id;
    }

    public void deleteDbContent(WiseSaying list, int input) {
        File jsonFile = new File(directoryPath + input + ".json");
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

    public void modifyDb(int id, String newContent, String newAuthor) {
        File jsonFile = new File(directoryPath + id + ".json");

        try (FileWriter fw = new FileWriter(jsonFile)) {
            obj.put("id", id);
            obj.put("content", newContent);
            obj.put("author", newAuthor);
            fw.write(obj.toJSONString());
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buildJson(int lastId) {
        File[] fileList = new File[lastId];
        File dataFile = new File(directoryPath + "data.json");
        try {
            JSONParser parser = new JSONParser();
            FileWriter fw = new FileWriter(dataFile);
            JsonArray jsonArray = new JsonArray();

            for (int i = 0; i < lastId; i++) {
                fileList[i] = new File(directoryPath + (i + 1) + ".json");
                FileReader reader = new FileReader(fileList[i]);
                Object obj = parser.parse(reader);
                JSONObject jsonObject = (JSONObject) obj;
                jsonArray.add(jsonObject.toJSONString());
            }
            fw.write(jsonArray.toString());
            fw.flush();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}