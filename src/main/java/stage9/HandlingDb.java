package stage9;

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
            obj.put("id", list.num.get(i));
            obj.put("content", list.speech.get(i));
            obj.put("author", list.author.get(i));
            fw.write(obj.toJSONString());
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveLastId(WiseSaying list) {
        try (FileWriter fw = new FileWriter(txtFile)) {
            fw.write(String.valueOf(list.num.size()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showDb(WiseSaying list, int i) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        File jsonFile = new File("C:\\workplace\\intellij\\20241118-wise-saying\\src\\main\\java\\db\\wiseSaying\\" + (i + 1) + ".json");

        if(jsonFile.exists()) {
            FileReader reader = new FileReader(jsonFile);
            Object obj = parser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;

            System.out.println(jsonObject.get("id") + " / " + jsonObject.get("content") + " / " + jsonObject.get("author"));
            return true;
        } else return false;
    }
}
