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

package org.izumo.ikong.spider.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.izumo.core.util.JacksonUtil;
import org.izumo.ikong.mongo.entity.Book;
import org.izumo.ikong.mongo.entity.bean.Chapter;
import org.izumo.ikong.mongo.entity.bean.ContentDetail;
import org.izumo.ikong.mongo.entity.bean.Volume;
import org.izumo.ikong.mongo.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import us.codecraft.webmagic.selector.Html;

@Service
public class SpiderService {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    @Value("#{properties['store.path']}")
    private String dirPath;

    public Book readBook(String url) {
        String[] tmp = url.split("/");
        String bid = tmp[tmp.length - 1];
        bid = bid.substring(0, bid.indexOf("."));
        Book book = new Book();
        book.setUrl(url);
        book.setBid(Integer.parseInt(bid));

        List<Volume> volumes = null;
        List<Chapter> chapters = null;

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        httpget.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.115 Safari/537.36");
        System.out.println("Executing request " + httpget.getRequestLine());
        CloseableHttpResponse response;

        try {
            response = httpclient.execute(httpget);
            System.out.println("----------------------------------------");
            String bodyAsString = EntityUtils.toString(response.getEntity());
            System.out.println("***********************************");

            Html html = new Html(new String(bodyAsString.getBytes("ISO-8859-1"), "UTF-8"));

            try {
                book.setAuthor(html.xpath(
                        "//table[@class='lk-book-detail ft-12 mt-10']/tbody/tr/td[@width='140']/a/text()").toString());
                book.setBrief(html.xpath("//p[@style='text-indent: 2em;' ]/text()").toString());
                book.setName(html.xpath("//div[@class='fn-left mb-20 ml-10']/h1[@class='ft-24']/strong/text()")
                        .toString());
                book.setPress(html.xpath("//table[@class='lk-book-detail ft-12 mt-10']/tbody/tr/td[6]/text()")
                        .toString());

                String cover = html.xpath("//div[@class='lk-book-cover ml-5']/a/img/@src").toString();
                book.setCover(this.saveImg(cover, bid, null));
            } catch (Exception e) {
                logger.error(e.getMessage(), e.getCause());
                e.printStackTrace();
            }

            volumes = new ArrayList<Volume>();
            Volume obj = null;
            Html vHtml = null;
            Html cHtml = null;
            String imageUrl = null;
            String vtmp = null;
            List<String> chapterStrings = null;
            Chapter cobj = null;

            //卷
            List<String> volumeStrings = html.xpath("//dd[@class='row well mg-15']").all();
            if (null != volumeStrings) {
                for (int i = 0; i < volumeStrings.size(); ++i) {
                    obj = new Volume();
                    vHtml = new Html(volumeStrings.get(i));
                    try {
                        imageUrl = vHtml.xpath("//div[@class='inline']/div/a/img/@src").toString();
                        obj.setImageUrl(this.saveImg(imageUrl, bid, null));
                        obj.setBookId(Integer.parseInt(bid));
                        obj.setIndex(i);

                        //head + name
                        vtmp = vHtml.xpath("//h2[@class='ft-24']/strong/a/text()").toString();
                        vtmp = vtmp.trim();
                        tmp = vtmp.split(" ");
                        if (tmp.length > 0) {
                            obj.setHeader(tmp[0]);
                        }
                        if (tmp.length > 2) {
                            obj.setName(tmp[2]);
                        }

                        //vid
                        vtmp = vHtml.xpath("//h2[@class='ft-24']/strong/a/@href").toString();
                        tmp = vtmp.split("/");
                        obj.setVolumeId(Integer.parseInt(tmp[tmp.length - 1].substring(0,
                                tmp[tmp.length - 1].indexOf("."))));

                        chapters = new ArrayList<Chapter>();
                        //chapters
                        chapterStrings = vHtml.xpath("//li[@class='inline']").all();
                        if (null != chapterStrings) {
                            for (int j = 0; j < chapterStrings.size(); ++j) {
                                cobj = new Chapter();
                                cHtml = new Html(chapterStrings.get(j));
                                try {
                                    cobj.setUrl(cHtml.xpath("//a/@href").toString());
                                    cobj.setIndex(j);
                                    cobj.setTitle(cHtml.xpath("//span[@class='lk-ellipsis inline']/text()").toString());
                                    tmp = cobj.getUrl().split("/");
                                    cobj.setId(Integer.parseInt(tmp[tmp.length - 1].substring(0,
                                            tmp[tmp.length - 1].indexOf("."))));
                                    cobj.setContentDetails(this.readChapter(cobj.getUrl(), bid, "" + obj.getVolumeId()));
                                    logger.info("======================sleep start==========================");
                                    Thread.sleep(10000);
                                    logger.info("======================sleep over==========================");
                                } catch (Exception e) {
                                    logger.error(e.getMessage(), e.getCause());
                                }
                                chapters.add(cobj);
                            }
                            obj.setChapters(chapters);
                        }

                        volumes.add(obj);
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e.getCause());
                        e.printStackTrace();
                    }

                }
            }

            book.setVolumes(volumes);

        } catch (ClientProtocolException e) {
            logger.error(e.getMessage(), e.getCause());
            e.printStackTrace();
        } catch (IOException e) {
            logger.error(e.getMessage(), e.getCause());
            e.printStackTrace();
        }
        System.out.println(JacksonUtil.obj2Str(book));
        return book;
    }

    public List<ContentDetail> readChapter(String url, String bid, String vid) {
        String[] tmp = url.split("/");
        String cid = tmp[tmp.length - 1];
        cid = cid.substring(0, cid.indexOf("."));

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        httpget.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.115 Safari/537.36");
        System.out.println("Executing request " + httpget.getRequestLine());
        CloseableHttpResponse response;

        List<ContentDetail> list = new ArrayList<ContentDetail>();
        ContentDetail obj = null;
        Html cHtml = null;

        try {
            response = httpclient.execute(httpget);
            System.out.println("----------------------------------------");
            String bodyAsString = EntityUtils.toString(response.getEntity());
            System.out.println("***********************************");

            Html html = new Html(new String(bodyAsString.getBytes("ISO-8859-1"), "UTF-8"));

            try {
                html.replace("lk-view-line J_comm", "lk-view-line");
                List<String> container = html.xpath("//div[@class='lk-view-line']").all();

                if (null != container) {
                    for (int i = 0; i < container.size(); ++i) {
                        cHtml = new Html(container.get(i));
                        obj = new ContentDetail();
                        obj.setId(i);
                        if (null != cHtml.xpath("//div[@class='lk-view-img']").toString()) {
                            obj.setC(this.saveImg(cHtml.xpath("//div[@class='lk-view-img']//a//@href").toString(), bid,
                                    vid));
                        } else {
                            obj.setC(cHtml.xpath("//div//text()").toString());
                        }
                        list.add(obj);
                    }
                }
                System.out.println(JacksonUtil.obj2Str(list));
                return list;
            } catch (Exception e) {
                logger.error(e.getMessage(), e.getCause());
                e.printStackTrace();
            }

        } catch (ClientProtocolException e) {
            logger.error(e.getMessage(), e.getCause());
            e.printStackTrace();
        } catch (IOException e) {
            logger.error(e.getMessage(), e.getCause());
            e.printStackTrace();
        }
        return null;
    }

    /* public Content readChapter(String url, String bid, String vid, String bookName) {
         String[] tmp = url.split("/");
         String cid = tmp[tmp.length - 1];
         cid = cid.substring(0, cid.indexOf("."));
         Content content = new Content();
         try {
             content.setChapterId(Integer.parseInt(cid));
             content.setVid(Integer.parseInt(vid));
             content.setBid(Integer.parseInt(bid));
         } catch (Exception e) {
             logger.error(e.getMessage(), e.getCause());
             e.printStackTrace();
         }

         content.setBookName(bookName);

         CloseableHttpClient httpclient = HttpClients.createDefault();
         HttpGet httpget = new HttpGet(url);
         httpget.setHeader("User-Agent",
                 "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.115 Safari/537.36");
         System.out.println("Executing request " + httpget.getRequestLine());
         CloseableHttpResponse response;

         List<ContentDetail> list = new ArrayList<ContentDetail>();
         ContentDetail obj = null;
         Html cHtml = null;
         String img = null;

         try {
             response = httpclient.execute(httpget);
             System.out.println("----------------------------------------");
             String bodyAsString = EntityUtils.toString(response.getEntity());
             System.out.println("***********************************");

             Html html = new Html(new String(bodyAsString.getBytes("ISO-8859-1"), "UTF-8"));

             try {
                 html.replace("lk-view-line J_comm", "lk-view-line");
                 List<String> container = html.xpath("//div[@class='lk-view-line']").all();

                 if (null != container) {
                     for (int i = 0; i < container.size(); ++i) {
                         cHtml = new Html(container.get(i));
                         obj = new ContentDetail();
                         obj.setId(i);
                         if (null != cHtml.xpath("//div[@class='lk-view-img']").toString()) {
                             obj.setC(this.saveImg(cHtml.xpath("//div[@class='lk-view-img']//a//@href").toString(), bid,
                                     vid));
                         } else {
                             obj.setC(cHtml.xpath("//div//text()").toString());
                         }
                         list.add(obj);
                     }
                 }
                 content.setList(list);
             } catch (Exception e) {
                 logger.error(e.getMessage(), e.getCause());
                 e.printStackTrace();
             }

         } catch (ClientProtocolException e) {
             logger.error(e.getMessage(), e.getCause());
             e.printStackTrace();
         } catch (IOException e) {
             logger.error(e.getMessage(), e.getCause());
             e.printStackTrace();
         }
         System.out.println(JacksonUtil.obj2Str(content));
         return content;
     }*/

    public String saveImg(String url, String bid, String vid) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String relatePath = "";
        if (null != bid && !bid.isEmpty()) {
            relatePath = relatePath + "/" + bid;
        }
        if (null != vid && !vid.isEmpty()) {
            relatePath = relatePath + "/" + vid;
        }
        relatePath = relatePath + "/image";
        File file = new File(this.dirPath + relatePath);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                return "";
            }
        }
        String fileName = sdf.format(new Date());
        relatePath = relatePath + "/" + fileName;
        String[] tmp = url.split(".");
        if (tmp.length > 0) {
            relatePath = relatePath + "." + tmp[tmp.length - 1];
        } else {
            relatePath = relatePath + ".jpg";
        }
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        httpget.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.115 Safari/537.36");
        //        httpget.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        //        httpget.setHeader("Accept-Encoding", "gzip, deflate, sdch");
        //        httpget.setHeader("Connection", "close");
        System.out.println("Executing request " + httpget.getRequestLine());
        CloseableHttpResponse response;
        try {
            response = httpclient.execute(httpget);
            System.out.println("----------------------------------------");
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream input = entity.getContent();

                OutputStream output = new FileOutputStream(new File(this.dirPath + relatePath));
                IOUtils.copy(input, output);
                output.flush();
                output.close();
                return relatePath;
            }
        } catch (ClientProtocolException e) {
            logger.error(e.getMessage(), e.getCause());
            e.printStackTrace();
        } catch (IOException e) {
            logger.error(e.getMessage(), e.getCause());
            e.printStackTrace();
        }
        return "";
    }
}
