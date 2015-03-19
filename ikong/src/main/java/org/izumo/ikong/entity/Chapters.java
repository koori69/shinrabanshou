package org.izumo.ikong.entity;

public class Chapters {

    /**
     * 章节内容id
     */
    private Integer Id;

    /**
     * 排序
     */
    private Integer Index;

    /**
     * 章节名称
     */
    private String Title;

    private Integer bookID = 0;

    private Integer volumeID = 0;

    public Integer getId() {
        return this.Id;
    }

    public void setId(Integer id) {
        this.Id = id;
    }

    public Integer getIndex() {
        return this.Index;
    }

    public void setIndex(Integer index) {
        this.Index = index;
    }

    public String getTitle() {
        return this.Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public Integer getBookID() {
        return this.bookID;
    }

    public void setBookID(Integer bookID) {
        this.bookID = bookID;
    }

    public Integer getVolumeID() {
        return this.volumeID;
    }

    public void setVolumeID(Integer volumeID) {
        this.volumeID = volumeID;
    }
}
