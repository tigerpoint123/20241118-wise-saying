import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stage11.WiseSayingController.WiseSayingController;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

//https://brunch.co.kr/@springboot/292
public class WiseSayingControllerTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void 등록테스트() {
        // 테스트 데이터 준비
        String testInput = "명언 1\n작가 1\n";
        ByteArrayInputStream in = new ByteArrayInputStream(testInput.getBytes());

        // 시스템 입력 스트림 변경 (테스트 전후에 원상복구 필요)
        System.setIn(in);

        // 테스트 객체 생성
        WiseSayingController controller = new WiseSayingController();

        // 테스트 실행
        controller.enroll(0);

        // 시스템 입력 스트림 원상복구
        System.setIn(System.in);

        // 테스트 결과 확인 (필요에 따라 추가적인 검증 로직 포함)
    }

    @Test
    void 목록테스트() {
        //given
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();
        WiseSayingController controller = new WiseSayingController();

        //when
        try {
            controller.showList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.setIn(System.in);
        //then
    }

    @Test
    void 삭제테스트() { // {"author":"작가 이름","id":1,"content":"명언 내용"}
        //given (준비)
        ByteArrayInputStream in = new ByteArrayInputStream("1\n".getBytes());
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();
        System.setIn(in);
        WiseSayingController controller = new WiseSayingController();

        //then (실행)
        controller.delete();
        System.setIn(System.in);

        //when(검증)
        Assertions.assertTrue(output.toString().trim().contains("명언이 삭제되었습니다"));
        TestUtil.clearSetOutToByteArray(output);
    }

    @Test
    void 수정테스트() {
        //given (준비)
        ByteArrayInputStream in = new ByteArrayInputStream("1\n명언 수정\n작가 수정\n".getBytes());
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();
        System.setIn(in);
        WiseSayingController controller = new WiseSayingController();

        //then (실행)
        controller.modify();
        System.setIn(System.in);

        //when(검증)
        Assertions.assertTrue(output.toString().trim().contains("명언이 수정되었습니다"));
        TestUtil.clearSetOutToByteArray(output);

    }

    @Test
    void 빌드테스트() {
        WiseSayingController controller = new WiseSayingController();
        controller.build();
    }

    @Test
    void 통합앱스트() {

    }

}
