package org.example.WiseSayingController;

import org.example.WiseSaying.WiseSaying;
import org.example.WiseSayingService.WiseSayingService;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

//고객의 명령을 입력받고 적절한 응답을 표현. 여기서 출력 스캐너 사용가능
public class WiseSayingController {
    private final WiseSayingService wiseSayingService;
    private final Scanner sc;
    private final WiseSaying wiseSaying;

    public WiseSayingController(Scanner scanner) {
        this.wiseSayingService = new WiseSayingService();
        this.sc = scanner;
        this.wiseSaying = new WiseSaying();
    }

    public void enroll(int i) {
        System.out.print("명언 : ");
        String speech = sc.nextLine();

        System.out.print("작가 : ");
        String author = sc.nextLine();

        wiseSaying.setId(i + 1); // i가 0부터니까
        wiseSaying.setContent(speech);
        wiseSaying.setAuthor(author);
        wiseSayingService.enrollService(wiseSaying);
        System.out.println((i+1) + "번 명언이 등록되었습니다.");
    }

    public void showList() throws IOException {
        System.out.println("번호 / 명언 / 작가");
        String[] fileName = wiseSayingService.getFileName();

        for (int i = 0; i < fileName.length; i++) {
            JSONObject obj = wiseSayingService.getDataService(i + 1);
            System.out.println(obj.get("id") + " / " + obj.get("content") + " / " + obj.get("author"));
        }
    }

    public void delete(String order) {
        int input = Integer.parseInt(order.split("=")[1]); // 삭제할려는 id

        if (wiseSayingService.isIdExist(input)) {
            wiseSayingService.deleteService(input);
            System.out.println(input + "번 명언이 삭제되었습니다.");
        } else {
            System.out.println(input + "번 명언이 존재하지 않습니다.");
        }
    }

    public void modify(String order) {
        int input = Integer.parseInt(order.split("=")[1]);

        try {
            if (wiseSayingService.isIdExist(input)) {
                System.out.println("명언(기존) : " + wiseSayingService.getDataService(input).get("content").toString());
                System.out.print("명언(수정) : ");
                String newContent = sc.nextLine();
                System.out.println("작가(기존) : " + wiseSayingService.getDataService(input).get("author").toString());
                System.out.print("작가(수정) : ");
                String newAuthor = sc.nextLine();

                wiseSaying.setAuthor(newAuthor);
                wiseSaying.setContent(newContent);
                wiseSaying.setId(input);

                wiseSayingService.modifyService(wiseSaying);
                System.out.println(input + "번 명언이 수정되었습니다.");
            } else {
                System.out.println(input + "번 명언이 존재하지 않습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void build() {
            wiseSayingService.build();
    }

    public void saveLastId(int i) throws IOException {
//        if (wiseSayingService.getLastId() < i) {
        wiseSayingService.saveLastIdService(i);
//        }
    }
}
