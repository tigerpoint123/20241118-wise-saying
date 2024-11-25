import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stage11.App.App;
import stage11.WiseSayingController.WiseSayingController;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import static org.junit.Assert.*;

public class WiseSayingControllerTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void 등록테스트() {
        //given (준비)
        ByteArrayInputStream in = new ByteArrayInputStream("명언 내용\n작가 이름\n".getBytes());
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();
        System.setIn(in);
        WiseSayingController controller = new WiseSayingController();

        //then (실행)
        controller.enroll(0);
        System.setIn(System.in);

        //when(검증)
        Assertions.assertTrue(output.toString().trim().contains("명언이 등록되었습니다"));
        TestUtil.clearSetOutToByteArray(output);
    }

    @Test
    void 통합앱테스트() throws IOException {
        //given
        App app = new App();
        ByteArrayInputStream in = new ByteArrayInputStream("등록\n현재를사랑하라\n작자미상\n".getBytes());
        System.setIn(in);

        //then
        app.run();

        // when
        System.setIn(System.in);
    }
}
