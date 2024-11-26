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
        int i =0;

        label:
        while (true) {
            System.out.print("명령) ");
            String order = sc.nextLine();

            if (order.equals("종료")) {
//                controller.saveLastId(i);
                break ;
            } else if (order.equals("등록")) {
                controller.enroll(i);
                i++;
            } else if (order.contains("목록")) {
                controller.showList(order);
            } else if (order.contains("삭제?id=")) {
                controller.delete(order);
            } else if (order.contains("수정?id=")) {
                controller.modify(order);
            } else if (order.equals("빌드")) {
                controller.build();
            }
        }
    }
}
