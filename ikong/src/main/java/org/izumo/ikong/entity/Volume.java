package org.izumo.ikong.entity;

public class Volume {
    /**
     * Bid
     */
    private Integer BookId;

    /**
     * 卷号
     */
    private String Header;

    /**
     * 图片地址
     */
    private String Imageurl;

    /**
     * 卷号排序
     */
    private Integer Index;

    /**
     * 简介
     */
    private String Introduction;

    /**
     * 更新时间
     */
    private String LastUpdate;

    /**
     * 名称
     */
    private String Name;

    /**
     * 卷号id 对应网站真实
     */
    private Integer VolumeId;

    public Integer getBookId() {
        return this.BookId;
    }

    public void setBookId(Integer bookId) {
        this.BookId = bookId;
    }

    public String getHeader() {
        return this.Header;
    }

    public void setHeader(String header) {
        this.Header = header;
    }

    public String getImageurl() {
        return this.Imageurl;
    }

    public void setImageurl(String imageurl) {
        this.Imageurl = imageurl;
    }

    public Integer getIndex() {
        return this.Index;
    }

    public void setIndex(Integer index) {
        this.Index = index;
    }

    public String getIntroduction() {
        return this.Introduction;
    }

    public void setIntroduction(String introduction) {
        this.Introduction = introduction;
    }

    public String getLastUpdate() {
        return this.LastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.LastUpdate = lastUpdate;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public Integer getVolumeId() {
        return this.VolumeId;
    }

    public void setVolumeId(Integer volumeId) {
        this.VolumeId = volumeId;
    }

    @Override
    public String toString() {
        return "Volume:BookId=" + this.BookId + "&Header=" + this.Header + "&Imageurl=" + this.Imageurl + "&Index="
                + this.Index + "&Introduction=" + this.Introduction + "&LastUpdate=" + this.LastUpdate + "&LastUpdate="
                + this.LastUpdate + "&VolumeId=" + this.VolumeId;
    }
}
