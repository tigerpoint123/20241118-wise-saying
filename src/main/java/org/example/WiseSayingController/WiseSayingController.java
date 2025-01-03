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
    private final WiseSaying wiseSaying;
    private final Scanner sc;
    private final int pageSize = 5; // 한 페이지에 보여줄 데이터 건수
    private final int currentPage = 1; // 기본값
    private int totalPage = 0; // 기본값

    public WiseSayingController(Scanner scanner) {
        this.wiseSaying = new WiseSaying();
        this.wiseSayingService = new WiseSayingService();
        this.sc = scanner;
    }

    public void enroll(int i) {
        System.out.print("명언 : ");
        String content = sc.nextLine();

        System.out.print("작가 : ");
        String author = sc.nextLine();

        i = wiseSayingService.getFileName().length;

        wiseSaying.setAuthor(author);
        wiseSaying.setContent(content);
        wiseSaying.setId(i + 1);
        wiseSayingService.enrollService(wiseSaying);
        wiseSayingService.saveLastIdService(i + 1);

        System.out.println((i + 1) + "번 명언이 등록되었습니다.");
    }

    public void showList(String order) throws IOException {
        String[] split = order.split("[=&?]"); //  (예시임)  목록?keywordType=content&keyword=명언
        List<WiseSaying> list;
        ObjectMapper mapper = new ObjectMapper();
        int totalData = getFileName().length; // 총 데이터 개수 = 10 (만약 11개라면 ?)

        if (order.contains("keyword")) { // 검색일때
            System.out.println("---------------------------");
            String keywordType = split[2];
            System.out.println("검색타입 : " + keywordType);
            String keyword = split[4];
            System.out.println("검색어 : " + keyword);
            System.out.println("---------------------------");
            System.out.println("번호 / 명언 / 작가");
            System.out.println("---------------------------");

            String str = wiseSayingService.searchService(keyword, keywordType);

            // json 문자열을 list<wisesaying> 객체로 변환
            list = mapper.readValue(str, mapper.getTypeFactory().constructCollectionType(List.class, WiseSaying.class));

            for (int i = 0; i < pageSize; i++) { // searchService 로 실행하는 repository 함수가 가장 큰 id부터 찾아옴.
                System.out.println(list.get(i).getId() + " / " + list.get(i).getContent() + " / " + list.get(i).getAuthor());
            }
            paging(list.size(), currentPage);

        } else if (order.contains("page")) { // 페이지번호일때  / 목록?page=2
            int searchPage = Integer.parseInt(split[2]);
            System.out.println("번호 / 명언 / 작가");
            System.out.println("---------------------------");

            // 전체 데이터가 11개일 때
            // 첫 페이지는 11부터 7 / 2페이지는 6부터 2 / 3페이지는 1
            // 그래서 i에 전체 개수 (11) 에서 (2페이지라 가정했을때) 11 - ( 2 - 1 ) * 5 를 해야 6부터 조회 가능.
            // 그 다음 6에서 i 가 > 1 해야하는데, 전체 11 - 한 페이지의 데이터 개수 5 * 현재 페이지 2 // 11 - 5 * 2 해서 1이 나옴.
            for (int i = totalData - (searchPage - 1) * pageSize; i > totalData - pageSize * searchPage; i--) { // 이걸 어떻게 생각한거지
                if (i == 0) break; // 이래야 5개가 없는 페이지는 1번 데이터 불러오고 끝.
                JSONObject obj = wiseSayingService.getDataService(i);
                System.out.println(obj.get("id") + " / " + obj.get("content") + " / " + obj.get("author"));
            }
            paging(totalData, searchPage);

        } else {
            for (int i = totalData; i > totalData - pageSize; i--) {
                JSONObject obj = wiseSayingService.getDataService(i);
                System.out.println(obj.get("id") + " / " + obj.get("content") + " / " + obj.get("author"));
            }
            System.out.println("---------------------------");

            paging(totalData, currentPage);
        }
    }

    private void paging(int totalData, int currentPage) {
        if (totalData % pageSize == 0) totalPage = totalData / pageSize;
        else totalPage = totalData / pageSize + 1;

        System.out.print("페이지 : ");
        for (int i = 1; i <= totalPage; i++) {
            if (i == currentPage) System.out.print("[" + i + "]");
            else System.out.print(i);
            if (i != totalPage) System.out.print(" / ");
            else System.out.println();
        }
    }

    public void delete(String order) {
        int input = Integer.parseInt(order.split("=")[1]); // 삭제할려는 id

        if (getFileName().length > 0) {
            wiseSayingService.deleteService(input);
            System.out.println(input + "번 명언이 삭제되었습니다.");
        } else {
            System.out.println(input + "번 명언이 존재하지 않습니다.");
        }
    }

    public void modify(String order) {
        int input = Integer.parseInt(order.split("=")[1]);

        try {
            if (getFileName().length > 0) {
                System.out.println("명언(기존) : " + wiseSayingService.getDataService(input).get("content").toString());
                System.out.print("명언(수정) : ");
                String newContent = sc.nextLine();
                System.out.println("작가(기존) : " + wiseSayingService.getDataService(input).get("author").toString());
                System.out.print("작가(수정) : ");
                String newAuthor = sc.nextLine();

                wiseSaying.setId(input);
                wiseSaying.setAuthor(newAuthor);
                wiseSaying.setContent(newContent);

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

    private String[] getFileName() {
        return wiseSayingService.getFileName();
    }
}
