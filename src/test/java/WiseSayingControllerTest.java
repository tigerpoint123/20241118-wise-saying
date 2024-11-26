import org.example.WiseSayingController.WiseSayingController;
import org.junit.jupiter.api.*;

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
        // given
        String testInput = """
                명언테스트
                작가테스트
                """;
        scanner = TestUtil.genScanner(testInput);

        // 테스트 객체 생성
        controller = new WiseSayingController(scanner);

        // when
        controller.enroll(0); //번호
        controller.saveLastId(1);

        // then
        Assertions.assertTrue(outContent.toString().contains("명언이 등록되었습니다"));
    }

    @Test
    void 목록테스트() {
        //given
        controller = new WiseSayingController(scanner);

        //when
        try {
            controller.showList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //then
        Assertions.assertTrue(outContent.toString().contains("1 / 명언 11 / 작가 11"));
    }

    @Test
    void 삭제테스트() { // {"author":"작가 이름","id":1,"content":"명언 내용"}
        //given (준비)
        String testInput = "1";
        scanner = TestUtil.genScanner(testInput);
        controller = new WiseSayingController(scanner);

        //then (실행)
        controller.delete("삭제?id="+1);
        System.setIn(System.in);

        //when(검증)
        Assertions.assertTrue(outContent.toString().trim().contains("명언이 삭제되었습니다"));
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
        controller.modify("수정?id="+1);
        System.setIn(System.in);

        //when(검증)
        Assertions.assertTrue(outContent.toString().trim().contains("명언이 수정되었습니다"));
    }

    @Test
    void 빌드테스트() {
        WiseSayingController controller = new WiseSayingController(scanner);
        controller.build();
    }

    @Test
    void 통합앱스트() {

    }
}
