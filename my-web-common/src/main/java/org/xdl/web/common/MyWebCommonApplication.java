package org.xdl.web.common;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author XieDuoLiang
 * @date 2021/11/17 下午3:42
 */
@SpringBootApplication
@MapperScan("org.xdl.web.common.mapper")
public class MyWebCommonApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyWebCommonApplication.class,args);
    }
}
