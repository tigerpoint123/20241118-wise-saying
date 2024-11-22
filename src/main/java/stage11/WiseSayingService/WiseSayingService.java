package stage11.WiseSayingService;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import stage11.WiseSaying.WiseSaying;
import stage11.WiseSayingRepository.WiseSayingRepository;

import java.io.IOException;

// 순수 비즈니스 로직. 스캐너 사용 금지. 출력 금지.
public class WiseSayingService {
    private static final WiseSayingRepository wiseSayingRepository = new WiseSayingRepository();

    public void enroll(WiseSaying list, int i) {
        wiseSayingRepository.saveDb(list, i);
        wiseSayingRepository.saveLastId(list);
    }

    public void showList(WiseSaying list) {
        try {
            int count = wiseSayingRepository.getLastId() - 48;
            for (int j = 0; j < count; j++) {
                if (!wiseSayingRepository.showDb(list, j)) {
                    if (list.id.get(j) > 0) {
                        System.out.println(list.id.get(j) + " / " + list.content.get(j) + " / " + list.author.get(j));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(WiseSaying list, int input) {
        wiseSayingRepository.deleteDbContent(list, input);
    }

    public void modify(int input, String newContent, String newAuthor) {
        wiseSayingRepository.modifyDb(input, newContent, newAuthor);
    }

    public void build() {

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

    public JSONObject getDataFromDb(WiseSaying list, int input) {
        try {
            return wiseSayingRepository.getDataFromDb(list, input, "originalContent");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
