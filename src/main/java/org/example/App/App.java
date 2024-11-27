package org.example.App;

import org.example.WiseSayingController.WiseSayingController;

import java.io.IOException;
import java.util.Scanner;

// 사용자 입력을 받고 그것이 controller에게 넘겨야 하는지 판단해서 맞으면 메서드 넘김. 스캐너 출력 사용가능
public class App {
    Scanner sc = new Scanner(System.in);
    WiseSayingController controller = new WiseSayingController(sc);

    public void run() throws IOException {
        System.out.println("== 명언 앱 ==");
        int i = 0;

        label:
        while (true) {
            System.out.print("명령) ");
            String order = sc.nextLine();
            String[] mainOrder = order.split("\\?");

            switch (mainOrder[0]) {
                case "종료":
                    break label;
                case "등록":
                    controller.enroll(i);
                    i++;
                    break;
                case "목록":
                    controller.showList(order);
                    break;
                case "삭제":
                    controller.delete(order);
                    break;
                case "수정":
                    controller.modify(order);
                    break;
                case "빌드":
                    controller.build();
                    break;
            }

        }
    }
}
