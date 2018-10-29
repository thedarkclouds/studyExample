package sqlexport;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @auther: huyue
 * @date: 2018/6/19 12:45
 * @description:
 * @modify by:
 */
public class ImportExcel {
    /**
     * 总行数
     */
    private int totalRows = 0;
    /**
     * 总列数
     */
    private int totalCells = 0;
    /**
     * 错误信息
     */
    private String errorInfo;

    /**
     * 构造方法
     */
    public ImportExcel() {
    }

    public int getTotalRows() {

        return totalRows;
    }

    public int getTotalCells() {

        return totalCells;
    }

    public String getErrorInfo() {

        return errorInfo;
    }

    public boolean validateExcel(String filePath) {

        /** 检查文件是否存在 */
        File file = new File(filePath);
        if (file == null || !file.exists()) {
            errorInfo = "文件不存在";
            return false;
        }
        return true;
    }



/*  public List<Map<Integer,Object>> read(String filePath) {
    List<Map<Integer,Object>> dataLst = new ArrayList<Map<Integer,Object>>();
    InputStream is = null;
    try {
      *//** 验证文件是否合法 *//*
      if (!validateExcel(filePath)) {
        System.out.println(errorInfo);
        return null;
      }
      *//** 调用本类提供的根据流读取的方法 *//*
      File file = new File(filePath);
      is = new FileInputStream(file);
      dataLst = read(is);
      is.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      if (is != null) {
        try {
          is.close();
        } catch (IOException e) {
          is = null;
          e.printStackTrace();
        }
      }
    }
    *//** 返回最后读取的结果 *//*
    return dataLst;
  }*/

    /**
     * @描述：根据流读取Excel文件
     * @参数：@param inputStream
     * @参数：@param
     * @参数：@return
     * @返回值：List
     */
    public List<Map<Integer, Object>> read(File file) {
        InputStream inputStream = null;
        String fileSuffix=null;
        List<Map<Integer, Object>> dataLst = null;
        Workbook wb = null;
        String name = file.getName();
        fileSuffix = name.substring(name.lastIndexOf(".") + 1, name.length());
        try {
            inputStream = new FileInputStream(file);

            if (fileSuffix.equals("xls")) {
                wb = new HSSFWorkbook(inputStream);
            } else if (fileSuffix.equals("xlsx")) {
                wb = new XSSFWorkbook(inputStream);
            }

            dataLst = read(wb);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataLst;
    }

    /**
     * @描述：读取数据
     * @参数：@param Workbook
     * @参数：@return
     * @返回值：List<List<String>>
     */
    private List<Map<Integer, Object>> read(Workbook wb) {
        List<Map<Integer, Object>> list = new ArrayList<Map<Integer, Object>>();
        //List<List<String>> dataLst = new ArrayList<List<String>>();
        /** 得到第一个shell */
        Sheet sheet = wb.getSheetAt(0);
        /** 得到Excel的行数 */
        this.totalRows = sheet.getPhysicalNumberOfRows();
        //最后一行的行号
        int rowNum = sheet.getLastRowNum();
        /** 得到Excel的列数 */
        if (this.totalRows >= 1 && sheet.getRow(0) != null) {
            //获得第一行的单元格数
            this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }
        /** 循环Excel的行   （不对表头进行处理） */
        for (int r = 1; r <= rowNum; r++) {
            // 获得当前行号的行
            Row row = sheet.getRow(r);

            Map<Integer, Object> dataMap = new HashMap<Integer, Object>();
            if (row == null) {
                //跳过当前循环
                continue;
            }

            /** 循环Excel的列 */
            for (int c = 0; c < this.getTotalCells(); c++) {
                //获得当前行的第c个单元格
                Cell cell = row.getCell(c);
                String cellValue = "";
                if (null != cell) {
                    // 以下是判断数据的类型
                    switch (cell.getCellType()) {
                        case XSSFCell.CELL_TYPE_NUMERIC: // 数字
                            //cellValue = +"";
                            //时间类型的数字处理
                            if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
                                SimpleDateFormat sdf = null;
                                if (cell.getCellStyle().getDataFormat() == HSSFDataFormat
                                        .getBuiltinFormat("h:mm")) {
                                    sdf = new SimpleDateFormat("HH:mm");
                                } else {// 日期
                                    sdf = new SimpleDateFormat("yyyy-MM-dd");
                                }
                                Date date = cell.getDateCellValue();
                                cellValue = sdf.format(date);
                            } else {
                                //对long数据处理
                                Long longValue = Math.round(cell.getNumericCellValue());
                                Double doubleVal = cell.getNumericCellValue();
                                //没有小数点的加小数点，解析为double类型。有，直接赋值
                                if (Double.parseDouble(longValue + ".0") == doubleVal) {   //判断是否含有小数位.0
                                    cellValue = longValue + "";
                                } else
                                    cellValue = doubleVal + "";
                            }

                            break;


                        case XSSFCell.CELL_TYPE_STRING: // 字符串
                            cellValue = cell.getStringCellValue();
                            break;
                        case XSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                            cellValue = cell.getBooleanCellValue() + "";
                            break;
                        case XSSFCell.CELL_TYPE_FORMULA: // 公式
                            cellValue = cell.getCellFormula() + "";
                            break;
                        case HSSFCell.CELL_TYPE_BLANK: // 空值
                            cellValue = "";
                            break;
                        case HSSFCell.CELL_TYPE_ERROR: // 故障
                            cellValue = "非法字符";
                            break;
                        default:
                            cellValue = "未知类型";
                            break;
                    }
                    dataMap.put(c, cellValue);
                }
            }
            list.add(dataMap);
        }
        return list;
    }


    public static void main(String[] args) {
  /*  InputStream inputStream=null;
    try{
      inputStream=new FileInputStream(new File("D:\\work\\76.xlsx"));
      List<Map<Integer,Object>> list=new ImportExcel().read(inputStream);
      Map<Integer,Object> map=list.get(0);
      map.size();
      System.out.println("size()=="+map.size());

      for (int i=0;i<map.size();i++){

       // System.out.println(i+"----"+map.get(i));
      }
      System.out.println(list);
    }catch (Exception e ){
      e.printStackTrace();

    }finally {
      try{
        inputStream.close();
      }catch (Exception e){
        e.printStackTrace();
      }
    }

  }*/
    }
}
