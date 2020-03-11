package com.xinghuo.ljjc.service;

import com.xinghuo.ljjc.pojo.ExcelInform;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName LinkCallService   链接调用检测
 * @Author sussen
 * @Version 1.0
 * @Data 2020/3/3
 */
@Service
@Slf4j
public class LinkCallService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ExcelHandlerService excelHandlerService;

    //存储失败链接的list
    private final List<ExcelInform> failLinkList = new ArrayList<>();

    /**
     * 请求链接，验证响应状态
     *
     * @param inform
     */
    @Async("myAsync")
    public void chickReq(ExcelInform inform) {

        String link = inform.getLink();
        //处理链接地址
        if (link != null && !"".equals(link)) {
            if (link.startsWith("http/")) {
                link = "http://" + link.substring(4, link.length() - 1);
            } else if (!link.startsWith("http://")) {
                link = "http://" + link;
            }

            try {
                //发送请求
                ResponseEntity<String> response = restTemplate.getForEntity(link, String.class);

                int statusCodeValue = response.getStatusCodeValue();
                //处理异常链接
                if (statusCodeValue == 200) {
                    log.info("[{}]正常链接",inform);
                } else {
                    log.warn("[{}]非200响应", inform);
                    failLinkList.add(inform);
                }
            } catch (RestClientException e) {
                log.warn("[{}]连接异常", inform);
                failLinkList.add(inform);
            }
        }
        //将失败链接写出excel
        excelHandlerService.writerExcel(failLinkList);
    }
}
