package org.example.db.wiseSaying;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException, ParseException {
//        JSONParser parser = new JSONParser();
//
//        File file = new File("C:\\workplace\\intellij\\20241118-wise-saying\\src\\main\\java\\org\\example\\db\\wiseSaying\\test.json");
//
//        FileReader reader = new FileReader(file);
//        Object obj = parser.parse(reader);
//        JSONObject jsonObject = (JSONObject) obj;
//        reader.close();
//        System.out.println(jsonObject.get("id"));

        File file = new File("C:\\workplace\\intellij\\20241118-wise-saying\\src\\main\\java\\org\\example\\db\\wiseSaying\\lastId.txt");
        FileReader reader = new FileReader(file);
        System.out.println(reader.read());
    }
}
