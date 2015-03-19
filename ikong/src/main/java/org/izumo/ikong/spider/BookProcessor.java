package org.izumo.ikong.spider;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.UrlUtils;

public class BookProcessor implements PageProcessor {
    private final Site site;

    public BookProcessor() {
        this.site = Site.me().setDomain(UrlUtils.getDomain("1")).setSleepTime(0);
        this.site.setCharset("UTF-8"); //UTF-8
    }

    public Site getSite() {
        return this.site;
    }

    public void process(Page page) {
        System.out.println(page.getRawText());

    }

}
