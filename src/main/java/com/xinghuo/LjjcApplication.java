package com.xinghuo;

import com.xinghuo.ljjc.service.ExcelHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @ClassName LjjcApplication   Excel文件链接检查
 * @Author sussen
 * @Version 1.0
 * @Data 2020/3/2
 */
@SpringBootApplication
@EnableScheduling
public class LjjcApplication extends SpringBootServletInitializer implements ApplicationRunner {

    @Autowired
    private ExcelHandlerService excelHandlerService;

    public static void main(String[] args) {
        SpringApplication.run(LjjcApplication.class,args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        //war包运行配置
        return builder.sources(LjjcApplication.class);
    }

    @Override
    public void run(ApplicationArguments args) {
        //首次运行初始化
        excelHandlerService.readExcel();
    }
}
