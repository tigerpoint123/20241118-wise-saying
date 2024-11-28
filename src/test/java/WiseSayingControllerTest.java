import org.assertj.core.api.Assertions;
import org.example.WiseSayingController.WiseSayingController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Scanner;

//https://brunch.co.kr/@springboot/292
public class WiseSayingControllerTest {
    private ByteArrayOutputStream outContent;
    private Scanner scanner;
    private WiseSayingController controller;

    @BeforeEach
    public void setUpStreams() {
        outContent = TestUtil.setOutToByteArray();
    }

    @AfterEach
    public void cleanUpStreams() {
        TestUtil.clearSetOutToByteArray(outContent);
    }

    @Test
    @DisplayName("등록테스트")
    void 등록테스트() throws IOException {
        for (int i = 6; i < 10; i++) {
            // given
            String testInput = "명언" + i + "\n작가" + i;
            scanner = TestUtil.genScanner(testInput);
            controller = new WiseSayingController(scanner);

            // when
            controller.enroll(i); // 가장 큰 json 파일 번호 쓰면 됨.

            // then
            Assertions.assertThat(outContent.toString().trim()).contains("명언이 등록되었습니다");
        }
    }

    @Test
    void 목록테스트() {
        //given
        String input = "목록?keywordType=author&keyword=작가";
        String[] split = input.split("=|&"); // 1 ,3 인덱스에 있음.

        scanner = TestUtil.genScanner(input);
        controller = new WiseSayingController(scanner);

        //when
        try {
            controller.showList(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //then
        Assertions.assertThat(outContent.toString().trim()).contains("1 / 명언테스트 / 작가테스트");
    }

    @Test
    void 삭제테스트() { // {"author":"작가11","id":1,"content":"명언11"}
        //given (준비)
        String testInput = "삭제?id=2";
        scanner = TestUtil.genScanner(testInput);
        controller = new WiseSayingController(scanner);

        //then (실행)
        controller.delete(testInput);
        System.setIn(System.in);

        //when(검증)
        Assertions.assertThat(outContent.toString().trim()).contains("명언이 삭제되었습니다");

    }

    @Test
    void 수정테스트() {
        //given (준비)
        String testInput = """
                명언수정
                작가수정
                """;
        scanner = TestUtil.genScanner(testInput);
        // 테스트 객체 생성
        controller = new WiseSayingController(scanner);

        //then (실행)
        controller.modify("수정?id=" + 1);
        System.setIn(System.in);

        //when(검증)
        Assertions.assertThat(outContent.toString().trim()).contains("명언이 수정되었습니다");
    }

    @Test
    void 빌드테스트() {
        WiseSayingController controller = new WiseSayingController(scanner);
        controller.build();
    }

}
