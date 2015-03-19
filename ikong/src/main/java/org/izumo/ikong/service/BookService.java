package org.izumo.ikong.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.izumo.ikong.entity.Book;
import org.izumo.ikong.entity.Volume;
import org.izumo.ikong.service.vo.BookList;
import org.izumo.ikong.spider.BookPipeline;
import org.izumo.ikong.spider.BookProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.selector.Html;

@Service
public class BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    public void search(String url) {
        Spider spider = Spider.create(new BookProcessor());
        spider.addUrl(url);
        spider.addPipeline(new BookPipeline());
        spider.thread(1).run();
    }

    public void test(String url) {
        String[] tmp = url.split("/");
        String bid = tmp[tmp.length - 1];
        bid = bid.substring(0, bid.indexOf("."));
        BookList list = new BookList();
        Book book = new Book();
        book.setBid(Integer.parseInt(bid));

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:11.0) Gecko/20100101 Firefox/11.0");
        System.out.println("Executing request " + httpget.getRequestLine());
        CloseableHttpResponse response;
        try {
            response = httpclient.execute(httpget);
            System.out.println("----------------------------------------");
            String bodyAsString = EntityUtils.toString(response.getEntity());
            System.out.println("***********************************");
            Html html = new Html(new String(bodyAsString.getBytes("ISO-8859-1"), "UTF-8"));
            System.out.println(html.getDocument().html());

            List<Volume> volumes = new ArrayList<Volume>();
            Volume obj = new Volume();
            book.setAuthor(html
                    .xpath("//table[@class='lk-book-detail ft-12 mt-10']/tbody/tr/td[@width='140']/a/text()")
                    .toString());
            book.setBrief(html.xpath("//p[@style='text-indent: 2em;' ]/text()").toString());
            book.setName(html.xpath("//div[@class='fn-left mb-20 ml-10']/h1[@class='ft-24']/strong/text()").toString());
            book.setPress(html.xpath("//table[@class='lk-book-detail ft-12 mt-10']/tbody/tr/td[6]/text()").toString());

            //章节
            List<String> voList = html.xpath("//div[@class='inline ml-15']").all();
            for (int i = 0; i < voList.size(); ++i) {

            }
            System.out.println(book);
        } catch (ClientProtocolException e) {
            logger.error(e.getMessage(), e.getCause());
            e.printStackTrace();
        } catch (IOException e) {
            logger.error(e.getMessage(), e.getCause());
            e.printStackTrace();
        }

    }

}
