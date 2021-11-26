package org.xdl.web.common.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author XieDuoLiang
 * @date 2021/11/17 下午3:57
 */
@RestController
@RequestMapping("/my")
public class MyController {

    @PostMapping("/test")
    public void test() {
        System.out.println("controller...");
    }

    @PostMapping("/test1")
    public void test1() throws InterruptedException {
        System.out.println("controller...test1");
        Thread.sleep(100);
    }
}
