package org.example;

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

명령) 수정?id=2
명언(기존) : 과거에 집착하지 마라.
명언 : 현재와 자신을 사랑하라.
작가(기존) : 작자미상
작가 : 홍길동
명령) 목록
번호 / 작가 / 명언
----------------------
2 / 홍길동 / 현재와 자신을 사랑하라.
명령) 종료
* */
public class Main {
    public static void main(String[] args) {
        WiseSaying list = new WiseSaying();

        System.out.println("== 명언 앱 ==");
        Scanner sc = new Scanner(System.in);
        int i = 0;
        int num = 1;

        while (true) {
            System.out.print("명령) ");
            String order = sc.nextLine();

            if (order.equals("종료")) {
                break;
            } else if (order.equals("등록")) {
                enroll(list, i, num, sc);
                i ++;
                num++;
            } else if (order.equals("목록")) {
                showList(list, i);
            } else if (order.equals("삭제")) {
                delete(list, i, sc);
            } else if (order.equals("수정")) {
                modify(list, i, sc);
            }
        }
    }

    private static void enroll(WiseSaying list, int i, int num, Scanner sc) {
        list.num[i] = num;

        System.out.print("명언 : ");
        String speech = sc.nextLine();
        list.speech[i] = speech;

        System.out.print("작가 : ");
        String author = sc.nextLine();
        list.author[i] = author;

        System.out.println(list.num[i] + "번 명언이 등록되었습니다.");
    }

    public static void showList(WiseSaying list, int i) {
        for (int j = 0; j < i; j++) {
            if(list.num[j] > 0) {
                System.out.println(list.num[j] + " / " + list.speech[j] + " / " + list.author[j]);
            }
        }
    }

    public static void delete(WiseSaying list, int i, Scanner sc) {
        System.out.print("id= ");
        int input = sc.nextInt();

        for(int k = 0; k < i; k++) {
            if(input==list.num[k]) {
                list.num[k] = 0;
                list.speech[k] = null;
                list.author[k] = null;
                System.out.println(input + "번 명언이 삭제되었습니다.");
            } else {
                System.out.println(input + "번 명언은 존재하지 않습니다.");
            }
        }
    }

    public static void modify(WiseSaying list, int i, Scanner sc) {
        System.out.print("id = ");
        int input = sc.nextInt();

        for (int k = 0; k < i; k++) {
            if (list.num[k] == input) {
                System.out.println("명언(기존) : " + list.speech[k]);
                System.out.print("명언(수정) : ");
                list.speech[k] = sc.nextLine();
                System.out.println("작가(기존) : " + list.author[k]);
                System.out.print("작가(수정) : ");
                list.author[k] = sc.nextLine();
            }
        }
    }

}

