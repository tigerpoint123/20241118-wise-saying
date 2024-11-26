package org.example.WiseSayingService;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.example.WiseSaying.WiseSaying;
import org.example.WiseSayingRepository.WiseSayingRepository;

import java.io.IOException;

// 순수 비즈니스 로직. 스캐너 사용 금지. 출력 금지.
public class WiseSayingService {
    private final WiseSayingRepository wiseSayingRepository;

    public WiseSayingService() {
        this.wiseSayingRepository = new WiseSayingRepository();
    }

    public void enrollService(WiseSaying wiseSaying) {
        wiseSayingRepository.enrollDb(wiseSaying);
    }

    public void deleteService(int input) {
        wiseSayingRepository.deleteDb(input);
    }

    public void modifyService(WiseSaying wiseSaying) {
        wiseSayingRepository.modifyDb(wiseSaying);
    }

    public void build() {
        wiseSayingRepository.buildJson();
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

    public JSONObject getDataService(int input) {
        try {
            return wiseSayingRepository.getDataFromDb(input);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveLastIdService(int i) {
        wiseSayingRepository.saveLastIdRepository(i);
    }

    public String[] getFileName() {
        return wiseSayingRepository.getFileListFromDb();
    }

    public String searchService(String keyword, String keywordType) {
        return wiseSayingRepository.searchDb(keyword, keywordType);
    }
}
