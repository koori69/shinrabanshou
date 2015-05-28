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

package org.izumo.ikong.spider.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.izumo.ikong.mongo.entity.Book;
import org.izumo.ikong.mongo.service.BookService;
import org.izumo.ikong.spider.service.SpiderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("spider")
public class SpiderAction {
    private static final Logger logger = LoggerFactory.getLogger(SpiderAction.class);

    @Value("#{properties['store.path']}")
    private String dirPath;

    @Autowired
    private SpiderService service;

    @Autowired
    private BookService bookService;

    @RequestMapping("test")
    public void test(String url) {
        this.service.saveImg(url, "2", "4");
    }

    @RequestMapping("read-book-all")
    public void readBook(String url) {
        Book book = this.service.readBook(url);
        if (null == this.bookService.save(book)) {
            System.err.println("Book insert error !!");
        }
        /*  if (null != book.getVolumes() && !book.getVolumes().isEmpty()) {
              List<Volume> list = book.getVolumes();
              List<Chapter> chapters = null;
              Chapter obj = null;
              Content content = null;
              for (int i = 0; i < list.size(); ++i) {
                  chapters = list.get(i).getChapters();
                  if (null != chapters && !chapters.isEmpty()) {
                      for (int j = 0; j < chapters.size(); ++j) {
                          obj = chapters.get(j);
                          content = this.contentService.saveByUrl(obj.getUrl(), book.getBid() + "", list.get(i)
                                  .getVolumeId() + "", book.getName());
                          if (null == content) {
                              System.err.println("Content insert error !! " + obj.getUrl());
                          }
                      }
                  }
              }
          }*/
        System.out.println("********************************************************************");
        System.out.println("**************************=OVER=************************************");
        System.out.println("********************************************************************");
    }

    @RequestMapping("img")
    public void img(String url) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        httpget.setHeader("",
                "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.115 Safari/537.36");
        httpget.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        httpget.setHeader("Accept-Encoding", "gzip, deflate, sdch");
        httpget.setHeader("Connection", "keep-alive");
        System.out.println("Executing request " + httpget.getRequestLine());
        CloseableHttpResponse response;
        try {
            response = httpclient.execute(httpget);
            System.out.println("----------------------------------------");
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream input = entity.getContent();
                OutputStream output = new FileOutputStream(new File(this.dirPath + "/" + sdf.format(new Date())
                        + ".jpg"));
                IOUtils.copy(input, output);
                output.flush();
                output.close();
                input.close();
            }
        } catch (ClientProtocolException e) {
            logger.error(e.getMessage(), e.getCause());
            e.printStackTrace();
        } catch (IOException e) {
            logger.error(e.getMessage(), e.getCause());
            e.printStackTrace();
        }
    }
}
