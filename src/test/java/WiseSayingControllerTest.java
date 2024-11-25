import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stage11.App.App;
import stage11.WiseSayingController.WiseSayingController;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        System.setIn(in);
        WiseSayingController controller = new WiseSayingController();

        //then (실행)
        controller.enroll(0);
        System.setIn(System.in);

        //when(검증)
        System.out.println("1번 명언이 등록되었습니다.");

    }

    @Test
    void 통합앱테스트() throws IOException {
        //given
        App app = new App();
        ByteArrayInputStream in = new ByteArrayInputStream("등록\n".getBytes());
        System.setIn(in);

        //when
        app.run();
        System.setIn(System.in);
    }
}
