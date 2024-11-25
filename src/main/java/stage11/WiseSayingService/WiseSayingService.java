package stage11.WiseSayingService;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import stage11.WiseSaying.WiseSaying;
import stage11.WiseSayingRepository.WiseSayingRepository;

import java.io.IOException;

// 순수 비즈니스 로직. 스캐너 사용 금지. 출력 금지.
public class WiseSayingService {
    private final WiseSayingRepository wiseSayingRepository;

    public WiseSayingService() {
        this.wiseSayingRepository = new WiseSayingRepository();
    }

    public void enroll(WiseSaying list, int i) {
        wiseSayingRepository.saveDb(list, i);
        wiseSayingRepository.saveLastId(list);
    }

    public int getLastId() throws IOException {
        return wiseSayingRepository.getLastId() - 48;
    }

    public void delete(WiseSaying list, int input) {
        wiseSayingRepository.deleteDbContent(list, input);
    }

    public void modify(int input, String newContent, String newAuthor) {
        wiseSayingRepository.modifyDb(input, newContent, newAuthor);
    }

    public void build(int lastId) {
        wiseSayingRepository.buildJson(lastId);
    }

    public boolean isIdExist(int input) {
        try {
            if (wiseSayingRepository.getLastId() - 48 < input || input == -1) { // 입력한게 db에 없음.
                return false;
            } else {
                return true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public JSONObject getDataFromDb(int input) {
        try {
            return wiseSayingRepository.getDataFromDb(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
