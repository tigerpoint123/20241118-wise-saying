package stage10;

import com.google.gson.JsonArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;

public class HandlingDb {
    File txtFile = new File("C:\\workplace\\intellij\\20241118-wise-saying\\src\\main\\java\\db\\wiseSaying\\lastId.txt");
    JSONObject obj = new JSONObject();

    public void saveDb(WiseSaying list, int i) {
        File jsonFile = new File("C:\\workplace\\intellij\\20241118-wise-saying\\src\\main\\java\\db\\wiseSaying\\" + (i + 1) + ".json");

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

    public int getOneId(int input) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        File jsonFile = new File("C:\\workplace\\intellij\\20241118-wise-saying\\src\\main\\java\\db\\wiseSaying\\" + input + ".json");
        FileReader reader = new FileReader(jsonFile);
        Object obj = parser.parse(reader);
        JSONObject jsonObject = (JSONObject) obj;

        int id = Integer.parseInt(jsonObject.get("id").toString());
        return id;
    }

    public String getDataFromDb(WiseSaying list, int id, String original) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        File jsonFile = new File("C:\\workplace\\intellij\\20241118-wise-saying\\src\\main\\java\\db\\wiseSaying\\" + id + ".json");
        FileReader reader = new FileReader(jsonFile);
        Object obj = parser.parse(reader);
        JSONObject jsonObject = (JSONObject) obj;

        if (original.equals("originalContent")) {
            return jsonObject.get("content").toString();
        } else {
            return jsonObject.get("author").toString();
        }
    }

    public int getLastId() throws IOException {
        FileReader reader = new FileReader(txtFile);
        int id = (char) reader.read();
        return id;
    }

    public boolean showDb(WiseSaying list, int i) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        File jsonFile = new File("C:\\workplace\\intellij\\20241118-wise-saying\\src\\main\\java\\db\\wiseSaying\\" + (i + 1) + ".json");

        if (jsonFile.exists()) {
            FileReader reader = new FileReader(jsonFile);
            Object obj = parser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;

            if (jsonObject.get("id") != null) {
                System.out.println(jsonObject.get("id") + " / " + jsonObject.get("content") + " / " + jsonObject.get("author"));
            }
            return true;
        } else return false;
    }

    public void deleteDbContent(WiseSaying list, int input) {
        File jsonFile = new File("C:\\workplace\\intellij\\20241118-wise-saying\\src\\main\\java\\db\\wiseSaying\\" + input + ".json");
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
        File jsonFile = new File("C:\\workplace\\intellij\\20241118-wise-saying\\src\\main\\java\\db\\wiseSaying\\" + id + ".json");

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

    public void mergeJson(int lastId) {
        File[] fileList = new File[lastId];
        File dataFile = new File("C:\\workplace\\intellij\\20241118-wise-saying\\src\\main\\java\\db\\wiseSaying\\data.json");
        try {
            JSONParser parser = new JSONParser();
            FileWriter fw = new FileWriter(dataFile);
            JsonArray jsonArray = new JsonArray();

            for(int i =1; i < lastId; i++) {
                fileList[i] = new File("C:\\workplace\\intellij\\20241118-wise-saying\\src\\main\\java\\db\\wiseSaying\\" + i + ".json");
                FileReader reader = new FileReader(fileList[i]);
                Object obj = parser.parse(reader);
                JSONObject jsonObject = (JSONObject) obj;
                jsonArray.add(jsonObject.toJSONString());
                fw.write(jsonArray.toString());
            }
            fw.flush();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkBuild() {

    }
}