/**
 * Copyright (c) 2015 https://github.com/koori69 All rights reserved.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 *
 */

package org.izumo.ikong.mongo.entity.bean;

import java.util.List;

public class Volume {
    /**
     * Bid
     */
    private Integer bookId;

    /**
     * 卷号
     */
    private String header;

    /**
     * 图片地址
     */
    private String imageUrl;

    /**
     * 卷号排序
     */
    private int index;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 更新时间
     */
    private String lastUpdate;

    /**
     * 名称
     */
    private String name;

    /**
     * 卷号id 对应网站真实
     */
    private Integer volumeId;

    /**
     * 阅读状态：0未开始 1看完
     */
    private int stauts = 0;

    private List<Chapter> chapters;

    public Integer getBookId() {
        return this.bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getHeader() {
        return this.header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getIntroduction() {
        return this.introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getLastUpdate() {
        return this.lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVolumeId() {
        return this.volumeId;
    }

    public void setVolumeId(Integer volumeId) {
        this.volumeId = volumeId;
    }

    public int getStauts() {
        return this.stauts;
    }

    public void setStauts(int stauts) {
        this.stauts = stauts;
    }

    public List<Chapter> getChapters() {
        return this.chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }
}
