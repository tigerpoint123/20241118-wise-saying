package org.example;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        WiseSaying list = new WiseSaying();
        JSONObject obj = new JSONObject();

        System.out.println("== 명언 앱 ==");
        Scanner sc = new Scanner(System.in);
        int i = 0;
        int num = 1;
        int lastId = getLastId();

        label:
        while (true) {
            System.out.print("명령) ");
//            System.out.printf("입력한 명령 : %s".formatted(sc.nextLine())); %s에 입력한 문자를 붙여서 출력함.
            String order = sc.nextLine();
            switch (order) {
                case "종료":
                    saveLastId(i);
                    break label;
                case "등록":
                    enroll(list, i, num, sc, obj, lastId);
                    i++;
                    num++;
                    break;
                case "목록":
                    showList(list, i, lastId);
                    break;
                case "삭제":
                    delete(list, i, sc);
                    break;
                case "수정":
                    modify(list, i, sc);
                    break;
            }
        }
        sc.close();
    }

    private static void saveLastId(int num) {
        File file = new File("C:\\workplace\\intellij\\20241118-wise-saying\\src\\main\\java\\org\\example\\db\\wiseSaying\\lastId.txt");
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(String.valueOf(num));
            System.out.println("저장  = "+num);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getLastId() throws IOException {
        File file = new File("C:\\workplace\\intellij\\20241118-wise-saying\\src\\main\\java\\org\\example\\db\\wiseSaying\\lastId.txt");
        FileReader reader = new FileReader(file);
        String id = String.valueOf(reader.read());
        System.out.println("읽기  = "+id);

        return Integer.parseInt(id);
    }

    private static void enroll(WiseSaying list, int i, int num, Scanner sc, JSONObject obj, int lastId) {
        File file = new File("C:\\workplace\\intellij\\20241118-wise-saying\\src\\main\\java\\org\\example\\db\\wiseSaying\\"
                + num + ".json");

        if(lastId == -1){
            list.num[i] = num;
            obj.put("id", list.num[i]);

            System.out.print("명언 : ");
            String speech = sc.nextLine();
            list.speech[i] = speech;
            obj.put("content", list.speech[i]);

            System.out.print("작가 : ");
            String author = sc.nextLine();
            list.author[i] = author;
            obj.put("author", list.author[i]);

            if (file.exists()) {
                obj.replace("id", list.num[i]);
                obj.replace("content", list.speech[i]);
                obj.replace("author", list.author[i]);

            } else {
                try {
                    FileWriter fw = new FileWriter(file, true);
                    fw.write(obj.toJSONString());
                    fw.flush();
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println(list.num[i] + "번 명언이 등록되었습니다.");
    }

    public static void showList(WiseSaying list, int i, int lastId) throws IOException, ParseException {
        JSONParser parser = new JSONParser();

        for (int j = 0; j < lastId; j++) {

            File file = new File("C:\\workplace\\intellij\\20241118-wise-saying\\src\\main\\java\\org\\example\\db\\wiseSaying\\"
                    + (j+1) + ".json");
            FileReader reader = new FileReader(file);
            Object obj = parser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;
            reader.close();

            if (!jsonObject.get("id").equals("0")) {
                System.out.println(jsonObject.get("id") + " / " + jsonObject.get("content") + " / " + jsonObject.get("author"));
            }
        }
    }

    public static void delete(WiseSaying list, int i, Scanner sc) {
        System.out.print("id= ");
        int input = Integer.parseInt(sc.nextLine());
        boolean result = false;

        for (int k = 0; k < i; k++) {
            if (input == list.num[k]) {
                list.num[k] = 0;
                list.speech[k] = null;
                list.author[k] = null;
                result = true;
                break;
            }
        }

        if (result) System.out.println(input + "번 명언이 삭제되었습니다.");
        else System.out.println(input + "번 명언이 존재하지 않습니다.");
    }

    public static void modify(WiseSaying list, int i, Scanner sc) {
        System.out.print("id = ");
        int input = Integer.parseInt(sc.nextLine());

        boolean result = false;

        for (int k = 0; k < i; k++) {
            if (input == list.num[k]) {
                result = true;
                Scanner sc2 = new Scanner(System.in);

                System.out.println("명언(기존) : " + list.speech[k]);

                System.out.print("명언(수정) : ");
                String newSpeech = sc2.nextLine();
                list.speech[k] = newSpeech;

                System.out.println("작가(기존) : " + list.author[k]);

                System.out.print("작가(수정) : ");
                String newAuthor = sc2.nextLine();
                list.author[k] = newAuthor;

                sc2.close();
                break;
            }
        }

        if (result)
            System.out.println("수정 완료");
        else
            System.out.println(input + "번 명언이 존재하지 않습니다.");
    }

}

