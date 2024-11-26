package org.example.WiseSaying;

//명언 객체(번호/명언내용/작가). mvc 모두 사용가능
public class WiseSaying {
    private String author;
    private String content;
    private Integer id;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
