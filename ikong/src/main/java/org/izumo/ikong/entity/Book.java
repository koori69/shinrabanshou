package org.izumo.ikong.entity;

public class Book {

    /**
     * 作者
     */
    private String Author;

    /**
     * 书id (不知有何作用) 一级文件夹目录名
     */
    private Integer Bid;

    /**
     * 简介
     */
    private String Brief;

    /**
     * 封面 img路径
     */
    private String Cover;

    /**
     * 书名
     */
    private String Name;

    /**
     * 出版社
     */
    private String Press;

    public String getAuthor() {
        return this.Author;
    }

    public void setAuthor(String author) {
        this.Author = author;
    }

    public Integer getBid() {
        return this.Bid;
    }

    public void setBid(Integer bid) {
        this.Bid = bid;
    }

    public String getBrief() {
        return this.Brief;
    }

    public void setBrief(String brief) {
        this.Brief = brief;
    }

    public String getCover() {
        return this.Cover;
    }

    public void setCover(String cover) {
        this.Cover = cover;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getPress() {
        return this.Press;
    }

    public void setPress(String press) {
        this.Press = press;
    }

    @Override
    public String toString() {
        return "Book:Author=" + this.Author + "&Bid=" + this.Bid + "&Brief=" + this.Brief + "&Cover=" + this.Cover
                + "&Name=" + this.Name + "&Press=" + this.Press;
    }
}
