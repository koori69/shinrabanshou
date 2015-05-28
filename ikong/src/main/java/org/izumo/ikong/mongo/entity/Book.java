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

package org.izumo.ikong.mongo.entity;

import java.util.List;

import org.izumo.ikong.mongo.entity.bean.Volume;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Book {
    /**
     * 作者
     */
    private String author;

    /**
     * 书id (不知有何作用) 一级文件夹目录名
     */
    private int bid;

    /**
     * 简介
     */
    private String brief;

    /**
     * 封面 img路径
     */
    private String cover;

    /**
     * 书名
     */
    private String name;

    /**
     * 出版社
     */
    private String press;

    /**
     * 页面url
     */
    private String url;

    /**
     * 状态：0继续，1完结
     */
    private int status;

    private List<Volume> volumes;

    public Book() {

    }

    @PersistenceConstructor
    public Book(String author, int bid, String brief, String cover, String name, String url, int status,
            List<Volume> volumes) {
        this.author = author;
        this.bid = bid;
        this.brief = brief;
        this.cover = cover;
        this.name = name;
        this.url = url;
        this.status = status;
        this.volumes = volumes;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getBid() {
        return this.bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public String getBrief() {
        return this.brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getCover() {
        return this.cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPress() {
        return this.press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Volume> getVolumes() {
        return this.volumes;
    }

    public void setVolumes(List<Volume> volumes) {
        this.volumes = volumes;
    }

}
