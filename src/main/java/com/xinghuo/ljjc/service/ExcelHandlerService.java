package com.xinghuo.ljjc.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.xinghuo.ljjc.pojo.ExcelInform;
import com.xinghuo.util.ExcelListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @ClassName ExcelHandlerService   excel文件处理
 * @Author sussen
 * @Version 1.0
 * @Data 2020/3/3
 */
@Service
@Slf4j
public class ExcelHandlerService {

    @Value("${linkChick.filePath}")
    private String filePath;
    @Value("${linkChick.failPath}")
    private String failPath;
    @Autowired
    private LinkCallService linkCallService;

    /**
     * 读取excel
     */
    public void readExcel() {
        File files = new File(filePath);
        String[] filelist = files.list();
        if (filelist != null)
            for (String file : filelist) {
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(filePath + file);

                    //excel对象状态监听
                    ExcelListener listener = new ExcelListener();

                    ExcelReader excelReader = EasyExcel.read(fis, ExcelInform.class, listener).build();
                    ReadSheet readSheet;

                    for (int i = 0; i < 50; i++) {
                        readSheet = EasyExcel.readSheet(i).build();
                        excelReader.read(readSheet);
                    }

                    List<ExcelInform> informList = listener.getRows();

                    for (ExcelInform inform : informList) {
                        linkCallService.chickReq(inform);
                    }
                    excelReader.finish();

                } catch (FileNotFoundException e) {
                    log.error("文件流异常", e);
                } finally {
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            log.error("文件流关闭异常", e);
                        }
                    }
                }
            }
    }

    /**
     * 写出excel
     *
     * @param failLinkList 失败链接集合
     */
    void writerExcel(List<ExcelInform> failLinkList) {
        if (failLinkList != null && failLinkList.size() > 0) {
            File failFile = new File(failPath);
            if (!failFile.exists()){
                failFile.mkdir();
            }
            String fileName = failPath + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "异常链接.xls";

            ExcelWriter excelWriter = EasyExcel.write(fileName, ExcelInform.class).build();
            WriteSheet writeSheet = EasyExcel.writerSheet("异常链接").build();
            excelWriter.write(failLinkList, writeSheet);

            excelWriter.finish();
        }
    }
}
