package org.example;


import java.util.ArrayList;
import java.util.Scanner;

//== 명언 앱 ==
//명령) 종료

/*
== 명언 앱 ==
명령) 등록
명언 : 현재를 사랑하라.
작가 : 작자미상
1번 명언이 등록되었습니다.
명령) 등록
명언 : 과거에 집착하지 마라.
작가 : 작자미상
2번 명언이 등록되었습니다.
명령) 목록
번호 / 작가 / 명언
----------------------
2 / 작자미상 / 과거에 집착하지 마라.
1 / 작자미상 / 현재를 사랑하라.
명령) 삭제?id=1
1번 명언이 삭제되었습니다.
명령) 종료
* */
public class Main {
    public static void main(String[] args) {
        System.out.println("== 명언 앱 ==");
        Scanner sc = new Scanner(System.in);
        ArrayList<String> list = new ArrayList<>();
        int i = 0;
        int num = 1;

        while (true) {
            System.out.print("명령) ");
            String order = sc.nextLine();

            if (order.equals("종료")) {
                break;
            } else if (order.equals("등록")) {
                list.add(String.valueOf(num));

                System.out.print("명언 : ");
                String speech = sc.nextLine();
                list.add(speech);

                System.out.print("작가 : ");
                String author = sc.nextLine();
                list.add(author);

                System.out.println(list.get(i) + "번 명언이 등록되었습니다.");
                i += 3;
                num++;
            } else if (order.equals("목록")) {
                for (int j = 0; j < list.size(); j+=3) {
                    System.out.println(list.get(j) + " / " + list.get(j+1) + " / " + list.get(j+2));
                }
            } else if (order.equals("삭제")) {
                System.out.print("id= ");
                String input = sc.nextLine();

                for(int k = 0; k < list.size(); k++) {
                    if(input.equals(list.get(k))) {
                        list.remove(k);
                        list.remove(k+1);
                        list.remove(k+2);
                        System.out.println(input + "번 명언이 삭제되었습니다.");
                        break;
                    } else {
                        System.out.println(input + "번 명언은 존재하지 않습니다.");
                    }
                }
            }

        }

    }
}