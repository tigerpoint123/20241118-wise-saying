package stage11.WiseSayingController;

import org.json.simple.JSONObject;
import stage11.WiseSaying.WiseSaying;
import stage11.WiseSayingService.WiseSayingService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//고객의 명령을 입력받고 적절한 응답을 표현. 여기서 출력 스캐너 사용가능
public class WiseSayingController {
    private final WiseSayingService wiseSayingService;
    private final Scanner sc;
    private List<WiseSaying> list;

    public WiseSayingController() {
        this.wiseSayingService = new WiseSayingService();
        this.sc = new Scanner(System.in);
        this.list = new ArrayList<>();
    }

//    public WiseSayingController(WiseSayingService wiseSayingService, Scanner sc) {
//        this.wiseSayingService = wiseSayingService;
//        this.sc = sc;
//    }

    public void enroll(int i) {
        list.add(i);

        System.out.print("명언 : ");
        String speech = sc.nextLine();
        list.add(speech);

        System.out.print("작가 : ");
        String author = sc.nextLine();
        list.author.add(author);

        wiseSayingService.enroll(list, i);
        System.out.println(list.id.get(i) + "번 명언이 등록되었습니다.");
    }

    public void showList() throws IOException {
        for (int i = 0; i < wiseSayingService.getLastId(); i++) {
            JSONObject obj = wiseSayingService.getDataFromDb(i+1);
            System.out.println(obj.get("id") + " / " + obj.get("content") + " / " + obj.get("author"));
        }
    }

    public void delete() {
        System.out.print("id= ");
        int input = Integer.parseInt(sc.nextLine()); // 삭제할려는 id

        if (wiseSayingService.isIdExist(input)) {
            wiseSayingService.delete(list, input);
            System.out.println(input + "번 명언이 삭제되었습니다.");
        } else {
            System.out.println(input + "번 명언이 존재하지 않습니다.");
        }
    }

    public void modify() {
        System.out.print("id = ");
        int input = Integer.parseInt(sc.nextLine());

        try {
            if (wiseSayingService.isIdExist(input)) {
                list.id.add(input);
                System.out.println("명언(기존) : " + wiseSayingService.getDataFromDb(input).get("content").toString());
                System.out.print("명언(수정) : ");
                String newContent = sc.nextLine();
                System.out.println("작가(기존) : " + wiseSayingService.getDataFromDb(input).get("author").toString());
                System.out.print("작가(수정) : ");
                String newAuthor = sc.nextLine();

                wiseSayingService.modify(input, newContent, newAuthor);
                System.out.println(input + "번 명언이 수정되었습니다.");
            } else {
                System.out.println(input + "번 명언이 존재하지 않습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void build() {
        try {
            wiseSayingService.build(wiseSayingService.getLastId());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
