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

public class Chapter {
    /**
     * 章节内容id
     */
    private int id;

    /**
     * 排序
     */
    private int index;

    /**
     * 章节名称
     */
    private String title;

    private final int bookId = 0;

    private final int volumeId = 0;

    private String url;

    /**
     * 阅读状态：0未开始 1看完
     */
    private int stauts;

    private List<ContentDetail> contentDetails;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBookId() {
        return this.bookId;
    }

    public int getVolumeId() {
        return this.volumeId;
    }

    public int getStauts() {
        return this.stauts;
    }

    public void setStauts(int stauts) {
        this.stauts = stauts;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<ContentDetail> getContentDetails() {
        return this.contentDetails;
    }

    public void setContentDetails(List<ContentDetail> contentDetails) {
        this.contentDetails = contentDetails;
    }
}
