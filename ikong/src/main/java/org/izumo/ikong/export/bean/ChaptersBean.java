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

package org.izumo.ikong.export.bean;

public class ChaptersBean {

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
