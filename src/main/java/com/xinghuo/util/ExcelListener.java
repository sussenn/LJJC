package com.xinghuo.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * excel对象监听器 [不能被spring托管]
 * @param <T>
 */
@Slf4j
public class ExcelListener<T extends BaseRowModel> extends AnalysisEventListener<T> {

    private List<T> rows = new ArrayList<>();

    /**
     * object是每一行数据映射的对象
     *
     * @param object
     * @param context
     */
    @Override
    public void invoke(T object, AnalysisContext context) {
        //log.info("当前行对象[{}]", object);
        rows.add(object);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("当前总共读取[{}]行", rows.size());
    }

    public List<T> getRows() {

        return rows;
    }
}
