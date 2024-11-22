package stage11.Main;

import org.json.simple.parser.ParseException;
import stage11.HandlingDb;
import stage11.WiseSaying.WiseSaying;

import java.io.IOException;
import java.util.Scanner;

// 컨트롤러 => 서비스 => 리포지터리 => 파일DB
public class Main {
    static Scanner sc = new Scanner(System.in);
    static HandlingDb handlingDb = new HandlingDb();

    public static void main(String[] args) throws IOException, ParseException {
        WiseSaying list = new WiseSaying();

        System.out.println("== 명언 앱 ==");
        int i = 0;

        label:
        while (true) {
            System.out.print("명령) ");
            String order = sc.nextLine();

            switch (order) {
                case "종료":
                    break label;
                case "등록":
                    enroll(list, i);
                    i++;
                    break;
                case "목록":
                    showList(list, i);
                    break;
                case "삭제":
                    delete(list, i);
                    break;
                case "수정":
                    modify(list, i);
                    break;
                case "빌드":
                    build(handlingDb.getLastId());
                    break;
            }
        }
    }
    private static void build(int lastId) throws IOException, ParseException {
        handlingDb.mergeJson(lastId);
    }

    private static void enroll(WiseSaying list, int i) throws IOException {
        list.id.add(i + 1);

        System.out.print("명언 : ");
        String speech = sc.nextLine();
        list.content.add(speech);

        System.out.print("작가 : ");
        String author = sc.nextLine();
        list.author.add(author);

        handlingDb.saveDb(list, i);
        handlingDb.saveLastId(list);
        System.out.println(list.id.get(i) + "번 명언이 등록되었습니다.");
    }

    private static void showList(WiseSaying list, int i) throws IOException, ParseException {
        int count = handlingDb.getLastId() - 48;
        for (int j = 0; j < count; j++) {
            if (!handlingDb.showDb(list, j)) {
                if (list.id.get(j) > 0) {
                    System.out.println(list.id.get(j) + " / " + list.content.get(j) + " / " + list.author.get(j));
                }
            }
        }
    }

    private static void delete(WiseSaying list, int i) throws IOException {
        System.out.print("id= ");
        int input = Integer.parseInt(sc.nextLine());
        boolean result = false;

        if (handlingDb.getLastId() < input) {
            System.out.println(input + "번 명언이 존재하지 않습니다.");
        } else {
            handlingDb.deleteDbContent(list, input);
            result = true;
        }
        if (result) System.out.println(input + "번 명언이 삭제되었습니다.");
    }

    private static void modify(WiseSaying list, int i) throws IOException, ParseException {
        System.out.print("id = ");
        int input = Integer.parseInt(sc.nextLine());
        int id = handlingDb.getOneId(input);

        if (handlingDb.getLastId() - 48 < id || id == -1) {
            System.out.println(input + "번 명언이 존재하지 않습니다.");
        } else {

            list.id.add(id);
            System.out.println("명언(기존) : " + handlingDb.getDataFromDb(list, id, "originalContent"));
            System.out.print("명언(수정) : ");
            String newContent = sc.nextLine();
            System.out.println("작가(기존) : " + handlingDb.getDataFromDb(list, id, "originalAuthor"));
            System.out.print("작가(수정) : ");
            String newAuthor = sc.nextLine();

            handlingDb.modifyDb(id, newContent, newAuthor);
        }
    }

}