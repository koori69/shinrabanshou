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

package org.izumo.ikong.mongo.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.izumo.ikong.mongo.dao.BookDao;
import org.izumo.ikong.mongo.entity.Book;
import org.izumo.ikong.spider.service.SpiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookDao dao;

    @Autowired
    private SpiderService service;

    public List<Book> findAll() {
        return this.dao.findAll();
    }

    public Book save(Book book) {
        return this.dao.save(book);
    }

    public Book saveByUrl(String url) {
        Book book = this.service.readBook(url);
        return this.dao.save(book);
    }

    public void deleteEntity(Book book) {
        this.dao.delete(book);
    }

    public void deleteById(ObjectId id) {
        this.dao.delete(id);
    }

    public void deleteByList(List<Book> list) {
        this.dao.delete(list);
    }
}
