package org.example.Main;

import org.json.simple.parser.ParseException;
import org.example.App.App;

import java.io.IOException;

// 컨트롤러 => 서비스 => 리포지터리 => 파일DB
public class Main {
    static App app = new App();

    public static void main(String[] args) throws IOException, ParseException {
        app.run();
    }
}