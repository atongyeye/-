package com.xx;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;


@ParentPackage(value="struts-default")
@Namespace(value="/")
public class ExcelAction extends ActionSupport{

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(ExcelAction.class);
    @Autowired
    private IDtaDetailService dtaDetailService;

    @Action(value="getExcel")
    public void ExportBankCheckInfo() {
        logger.info("DTA detail report export");
        //得到所要导出的数据
        List<DTADetailVO> errLst = dtaDetailService.getDTADetailList();
        HttpServletResponse response = ServletActionContext.getResponse();
        //定义导出excel的名字
        String excelName="四海游申报报表"+new SimpleDateFormat("yyyy-MM-dd").format(
                new Date()).toString();
        // 获取需要转出的excle表头的map字段
        LinkedHashMap<String, String> fieldMap =new LinkedHashMap<String, String>() ;
        fieldMap.put("policyNumber", "保单号码");
        fieldMap.put("insuredName", "被保人姓名");
        fieldMap.put("insuredIdNumber", "被保人证件号码");
        fieldMap.put("declareStartDate", "申报保障起始日");
        fieldMap.put("declareEndDate", "申报保障结束日");
        fieldMap.put("declareCreateDate", "申报提交日期");
        fieldMap.put("declareChannel", "申报渠道");
        fieldMap.put("declareState", "申报状态");
        fieldMap.put("declareCancelDate", "取消日期");
        fieldMap.put("declareCancelChannel", "取消渠道");
        logger.info("Set the report header ");
        //导出申报相关信息
		new ExportExcelUtils().export(excelName, errLst, fieldMap, response);
    }

}
