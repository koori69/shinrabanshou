package org.izumo.ikong.action;

import org.izumo.ikong.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class IKongApiAction {

    @Autowired
    private BookService service;

    @RequestMapping("test")
    public void test() {
        this.service.test("http://lknovel.lightnovel.cn/main/vollist/458.html");//http://lknovel.lightnovel.cn/main/vollist/458.html
    }
}
