package com.xinghuo.ljjc.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName ExcelInform    导出到Excel的实体
 * @Author sussen
 * @Version 1.0
 * @Data 2020/3/2
 */
@Data
public class ExcelInform extends BaseRowModel implements Serializable {

    private static final long serialVersionUID = 7020804031780452955L;
    @ExcelProperty(value ="单位",index = 1)
    private String unit;
    @ExcelProperty(value ="链接",index = 2)
    private String linkName;
    @ExcelProperty(value ="地址",index = 3)
    private String link;
}
