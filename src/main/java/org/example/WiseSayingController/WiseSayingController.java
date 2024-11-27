package org.example.WiseSayingController;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.WiseSaying.WiseSaying;
import org.example.WiseSayingService.WiseSayingService;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.List;
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

        i = wiseSayingService.getFileName().length;

        wiseSaying.setId(i + 1); // i가 0부터니까
        wiseSaying.setContent(speech);
        wiseSaying.setAuthor(author);

        wiseSayingService.enrollService(wiseSaying);
        wiseSayingService.saveLastIdService(i + 1);

        System.out.println((i + 1) + "번 명언이 등록되었습니다.");
    }

    //이 단계부터는 목록명령어에서 모든 명언을 볼 수 없고 페이징 된다.
    //샘플 데이터 명언 10개 생성
    //한 페이지에 최대 5개의 명언이 노출
    //페이지 번호를 생략하면 1 페이지로 간주합니다.
    //최신글이 우선적으로 나와야 합니다.
    //검색어를 적용하면 그것을 반영한 페이징이 되어야 합니다.
    public void showList(String order) throws IOException {
        String[] fileName = wiseSayingService.getFileName();
        String[] split = order.split("[=&?]"); //  (예시임)  목록?keywordType=content&keyword=작자

        int pageSize = 5; // 한 페이지에 보여줄 데이터 건수
        int currentPage; //현재 보고있는 페이지
        int totalData = fileName.length; // 총 데이터 개수 = 10

        if (order.contains("?") && split[1].contains("keyword")) { // 검색이  있을때
            System.out.println("---------------------------");
            String keywordType = split[2];
            System.out.println("검색타입 : " + keywordType);
            String keyword = split[4];
            System.out.println("검색어 : " + keyword);
            System.out.println("---------------------------");
            System.out.println("번호 / 명언 / 작가");
            System.out.println("---------------------------");

            String str = wiseSayingService.searchService(keyword, keywordType);
            ObjectMapper mapper = new ObjectMapper();

            // json 문자열을 list<wisesaying> 객체로 변환
            List<WiseSaying> list = mapper.readValue(str, mapper.getTypeFactory().constructCollectionType(List.class, WiseSaying.class));

            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i).getId() + " / " + list.get(i).getContent() + " / " + list.get(i).getAuthor());
            }

        }else { // 검색이랑 페이지번호 없을때
            System.out.println("번호 / 명언 / 작가");
            System.out.println("---------------------------");

            currentPage = 1;
            for (int i = totalData; i > totalData - pageSize; i--) {
                JSONObject obj = wiseSayingService.getDataService(i);
                System.out.println(obj.get("id") + " / " + obj.get("content") + " / " + obj.get("author"));
            }
            System.out.println("---------------------------");

            System.out.println("페이지 : [" + (currentPage) + "] / " + (totalData / pageSize));
        }
    }

    public void delete(String order) {
        String[] fileName = wiseSayingService.getFileName();
        int input = Integer.parseInt(order.split("=")[1]); // 삭제할려는 id

        if (fileName.length > 0) {
            wiseSayingService.deleteService(input);
            System.out.println(input + "번 명언이 삭제되었습니다.");
        } else {
            System.out.println(input + "번 명언이 존재하지 않습니다.");
        }
    }

    public void modify(String order) {
        int input = Integer.parseInt(order.split("=")[1]);
        String[] fileName = wiseSayingService.getFileName();

        try {
            if (fileName.length > 0) {
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
}
