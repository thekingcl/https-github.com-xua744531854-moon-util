package com.moon.office.excel;

import com.moon.io.FileUtil;
import com.moon.office.excel.core.*;
import com.moon.office.excel.enums.ValueType;
import com.moon.util.Console;
import com.moon.util.DateUtil;
import com.moon.util.assertions.Assertions;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static com.moon.util.assertions.Assertions.of;

/**
 * @author benshaoye
 */
class ExcelUtilTestTest {

    static final Assertions assertions = of();

    Workbook result;

    @Test
    void testRender() {
        assertions.assertThrows(() -> ExcelUtil.render());
    }

    Object data = new HashMap() {{
        put("name", 1);
    }};

    @TableExcel(type = TableExcel.Type.XLS, value = {
        @TableSheet({
            @TableRow({
                @TableCell(value = "'序号'", rowspan = "2"),
                @TableCell(value = "'基本信息'", colspan = "5"),
                @TableCell(value = "'商业医疗保险'", rowspan = "2"),
                @TableCell(value = "'社保信息'", colspan = "3"),
            }),
            @TableRow({
                @TableCell("'姓名'"),
                @TableCell("'性别'"),
                @TableCell("'年龄'"),
                @TableCell("'身份证号'"),
                @TableCell("'家庭住址'"),
                @TableCell("'医疗保险'"),
                @TableCell("'养老保险'"),
                @TableCell("'生育保险'"),
            }),
            @TableRow(var = "$var:20", value = {
                @TableCell("$var+1"),
                @TableCell("'张三'"),
                @TableCell("'男'"),
                @TableCell("'25'"),
                @TableCell("'100100199102025456'"),
                @TableCell("'北京东城'"),
                @TableCell("'未参加'"),
                @TableCell("'5000'"),
                @TableCell("'5000'"),
                @TableCell("'5000'"),
            })
        })
    })
    @Test
    void testGetTableRendererAnnotation() throws IOException {
        String path = "E:/test/test-" + DateUtil.now() + ".xls";
        Console.out.println(DateUtil.format());
        Console.out.time();
        result = ExcelUtil.render(data);
        Console.out.timeEnd();
        Console.out.println(DateUtil.format());
        Console.out.time();
        result.write(FileUtil.getFileOutputStream(path));
        Console.out.timeEnd();
        Console.out.println(DateUtil.format());
    }

    @TableExcel(
        value = {
            @TableSheet(sheetName = "'个人信息导出'", value = {
                @TableRow({
                    @TableCell(value = "'序号'", rowspan = "2"),
                    @TableCell(value = "'基本信息'", colspan = "3"),
                    @TableCell(value = "'时间'", rowspan = "2", colspan = "2"),
                    @TableCell(value = "'详细信息'", colspan = "3"),
                }),
                @TableRow({
                    @TableCell("'姓名'"),
                    @TableCell("'年龄'"),
                    @TableCell("'性别'"),
                    @TableCell(value = "'身份证号'", className = "id-card-style"),
                    @TableCell("'户籍地址'"),
                    @TableCell("'通信地址'"),
                }),
                @TableRow(var = "($var,$index)=employee", value = {
                    @TableCell("$index + 1"),
                    @TableCell("$var.name"),
                    @TableCell(value = "$var.age", className = "align-right", type = ValueType.NUMERIC),
                    @TableCell("$var.sex"),
                    @TableCell(value = "@DateUtil.format()", colspan = "2"),
                    @TableCell("$var['idCard']"),
                    @TableCell("$var['idAddress']"),
                    @TableCell("$var.address"),
                })
            }),
            @TableSheet(sheetName = "'统计信息'")
        })
    @Test
    void testExportExcel() {
        String path = "E:/test/test-" + DateUtil.now() + ".xls";
        data = new HashMap() {{
            put("employee", new Employee());
        }};
        result = ExcelUtil.render(data);
        try {
            result.write(FileUtil.getFileOutputStream(path));
        } catch (Exception e) {
            // ignore
        }
    }

    // 可用 type 指定生成的 excel 类型
    @TableExcel(type = TableExcel.Type.XLS, value = {
        @TableSheet(var = "$data = {sheet: '新建', name: '张三', age: 25, sex: '男', idCard: '100100199102025456'}",
            sheetName = "sheet",// 将创建一个名为 “新建” 的 sheet
            value = {
                @TableRow({
                    // 可用指定 sheet 名，由 [RunnerUtil](#RunnerUtil) 运行，单引号包裹的才是字符串
                    @TableCell("'序号'"),
                    @TableCell("'姓名'"),
                    @TableCell("'年龄'"),
                    @TableCell("'性别'"),
                    @TableCell("'身份证号'"),
                }),
                @TableRow(var = "$var in 100", value = {
                    @TableCell("$var +         1"),
                    @TableCell("$data.name"),
                    @TableCell("$data.age"),
                    @TableCell("$data.sex"),
                    @TableCell("$data.idCard"),
                })
            })
    })
    @Test
    void testInstance() throws IOException {
        String path = "E:/test/test-" + DateUtil.now() + ".xls";
        result = ExcelUtil.render();// 生成 Workbook 文档

        assertions.assertInstanceOf(result, HSSFWorkbook.class);
        int number = result.getNumberOfSheets();
        assertions.assertEq(number, 1);
        Sheet sheet = result.getSheetAt(0);
        assertions.assertEq(sheet.getLastRowNum(), 100);
        Row row = sheet.getRow(100);
        assertions.assertNotNull(row);
        Cell cell = row.getCell(1);
        assertions.assertEquals(cell.getStringCellValue(), "张三");
    }

    @TableExcel(value = {
        @TableSheet(sheetName = "'企业订单追踪'", value = {
            @TableRow(height = 600, value = {
                @TableCell(colspan = "14", value = "'企业订单追踪表'", className = "align-center border-bottom")
            }),
            @TableRow(value = {
                @TableCell(className = "align-center", value = "'序号'", rowspan = "2"),
                @TableCell(className = "align-center", value = "'企业名称'", rowspan = "2"),
                @TableCell(className = "align-center border-right", value = "'业务月份'", rowspan = "2"),
                @TableCell(className = "align-center border-right", value = "'订单生成'", colspan = "3"),
                @TableCell(className = "align-center border-right", value = "'付款&发票'", colspan = "4"),
                @TableCell(className = "align-center border-right", value = "'分发&处理'", colspan = "3"),
                @TableCell(className = "align-center", value = "'完成情况'", rowspan = "2"),
            }),
            @TableRow(value = {
                @TableCell(className = "align-center border-left", value = "'在册人员'"),
                @TableCell(className = "align-center", value = "'已生成人员'"),
                @TableCell(className = "align-center border-right", value = "'订单生成'"),
                @TableCell(className = "align-center", value = "'客户付款'", width = 3000),
                @TableCell(className = "align-center", value = "'客户申请发票'", width = 4000),
                @TableCell(className = "align-center", value = "'业务员申请发票'", width = 4000),
                @TableCell(className = "align-center border-right", value = "'财务开具发票'", width = 4000),
                @TableCell(className = "align-center", value = "'前道分发'", width = 3000),
                @TableCell(className = "align-center", value = "'后道分发'", width = 3000),
                @TableCell(className = "align-center border-right", value = "'后道处理'", width = 3000),
            })
        })
    })
    @Test
    void testExportEnterpriseExcel() {
        String path = "E:/test/test-" + DateUtil.now() + ".xls";
        result = ExcelUtil.render();// 生成 Workbook 文档

        try {
            result.write(FileUtil.getFileOutputStream(path));
        } catch (Exception e) {
            // ignore
        }
    }

    public static class Employee {
        private String name = "张三";
        private int age = 25;
        private String sex = "男";
        private String idCard = "100100199102025456";
        private String idAddress = "北京东城区";
        private String address = "北京西城区";

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getIdAddress() {
            return idAddress;
        }

        public void setIdAddress(String idAddress) {
            this.idAddress = idAddress;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}