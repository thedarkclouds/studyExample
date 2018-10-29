package sqlexport;





import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


import org.apache.poi.hssf.usermodel.HSSFCellStyle;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;


/**
 * @auther: huyue
 * @date: 2018/6/7 15:57
 * @description:
 * @modify by:
 */


public class ExcelPortUtil {

/** 总行数 */

  private int totalRows = 0;

/** 总列数 */

  private int totalCells = 0;

/** 错误信息 */

  private String errorInfo;

/** 构造方法 */

  public ExcelPortUtil() {
  }

    private static final String  dateTime="yyyy-MM-dd";
    private static final String  dateSecond="yyyy-MM-dd HH:mm:ss";
    private static final String Error_EXCELPORTUTIL  ="ExcelPortUtil.exportExcel";
    private  static  int p=0;
    public static void exportExcel(List<ExcelHeader>listHead, List<Object> list) {
      SXSSFWorkbook workbook = null;

      try {
        SimpleDateFormat formatDatetime = new SimpleDateFormat(dateSecond);
        SimpleDateFormat formatDate = new SimpleDateFormat(dateTime);
        // 创建一个Excel文件
        //XSSFWorkbook workbook = new XSSFWorkbook();

        workbook = new SXSSFWorkbook(50000);
        // 创建一个工作表
        SXSSFSheet sheet = workbook.createSheet("Sheet");
        // 添加表头行
        SXSSFRow hssfRow = sheet.createRow(0);
        // 设置单元格格式居中
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        // 添加表头内容
        SXSSFCell headCell = null;
        for (int i = 0; i < listHead.size(); i++) {
          headCell = hssfRow.createCell(i);
          headCell.setCellValue(listHead.get(i).getTittle());
          headCell.setCellStyle(cellStyle);
        }
        //无数据写出表头
        if (list.size() != 0) {
          // workbook.write(response.getOutputStream());

          Map<String, Integer> map = new HashMap<>();
          String k = null;
           //单元格数量
          for (int i = 0; i < listHead.size(); i++) {
            //反射获取对象所有成员变量
            Field[] fields = list.get(0).getClass().getDeclaredFields();
             //表头大于单个数据长度，结束
            if (i + 1 > fields.length) {
              break;
            }
            //行数
            for (int j = 0; j < list.size(); j++) {
              k = (j + 1) + "";
              //
              if (map.get(k) == null) {
                //创建第n行
                hssfRow = sheet.createRow((int) j + 1);
              } else {
                //当前行已经创建的，直接拿到该行的对象
                hssfRow = sheet.getRow(map.get(k));
              }



              map.put(k, (j + 1));

              //获得对象对应的成员变量的域
              Field field = list.get(j).getClass().getDeclaredField(listHead.get(i).getName());
              //获的成员变量的值
              String name = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
             //获得get方法
              Method m = list.get(j).getClass().getMethod("get" + name);
              //()m.invoke(list.get(j));

              if ("txt".equals(listHead.get(i).getDataType())) {
                String value = (String) m.invoke(list.get(j));
                //获得当前行的第i个单元格
                p=i;
                SXSSFCell cell = hssfRow.createCell(i);
                //封装值
                cell.setCellValue(value);
                //封装样式
                cell.setCellStyle(cellStyle);
              }
              if ("datetime".equals(listHead.get(i).getDataType())) {
                String date = (String) m.invoke(list.get(j));
                Date datetime = formatDatetime.parse(date);
                SXSSFCell cell = hssfRow.createCell(i);
                short df = workbook.createDataFormat().getFormat(dateSecond);
                CellStyle style = workbook.createCellStyle();
                style.setDataFormat(df);
                cell.setCellValue(datetime);
                cell.setCellStyle(style);
              }
              if ("date".equals(listHead.get(i).getDataType())) {
                String dateStr = (String) m.invoke(list.get(j));
                Date date = formatDate.parse(dateStr);
                SXSSFCell cell = hssfRow.createCell(i);
                short df = workbook.createDataFormat().getFormat(dateTime);
                CellStyle style = workbook.createCellStyle();
                style.setDataFormat(df);
                cell.setCellValue(date);
                cell.setCellStyle(style);
              }
              if ("num".equals(listHead.get(i).getDataType())) {
                String numStr = (String) m.invoke(list.get(j));
                if (numStr.indexOf(".") == -1) {
                  Integer num = Integer.parseInt(numStr);
                  SXSSFCell cell = hssfRow.createCell(i);
                  cell.setCellValue(num);
                  cell.setCellStyle(cellStyle);
                } else {
                  Double.parseDouble(numStr);
                  BigDecimal cash = new BigDecimal(Double.parseDouble(numStr));
                  //小数点精确4位
                  double d2 = cash.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                  SXSSFCell cell = hssfRow.createCell(i);
                  cell.setCellValue(d2);
                  cell.setCellStyle(cellStyle);
                }
              }
            }
          }
        }
        String dateTime= formatDatetime.format(new Date());
          //输出流

         //文件名称定义
       // String fileName = dateTime.replace("-","").replace("_","")+"-"+UUID.randomUUID().toString().replace("-","")+".xlsx";
        //清空缓冲区
       // response.reset();
        //设置文本格式类型
     //   response.setContentType("application/msexcel;charset=utf-8");
       //设置头
    //    response.setHeader("Content-disposition", "attachment;filename= "+ fileName);
        //写入返回流

        String fileName=UUID.randomUUID().toString().replace("-","")+".xlsx";

        fileName="d:\\"+fileName;
        OutputStream output = new FileOutputStream(fileName);
        workbook.write(output);

      } catch (Exception e) {



      }finally {
        try{
          workbook.close();

        }catch (Exception e){

        }

      }
    }


  public static void main(String[] args) {
    ExcelHeader excelHeader1=new ExcelHeader("表头1","id","num");
    ExcelHeader excelHeader2=new ExcelHeader("表头2","name","txt");
    ExcelHeader excelHeader3=new ExcelHeader("表头3","date","date");
    ExcelHeader excelHeader4=new ExcelHeader("表头4","date","date");
    List<ExcelHeader>listHead=new ArrayList<>();
    listHead.add(excelHeader1);
    listHead.add(excelHeader2);
    listHead.add(excelHeader3);
    listHead.add(excelHeader4);
    List<Object> list=new ArrayList<>();
    TUser user=new TUser();
    user.setId("2");
    user.setName("马");
    user.setDate("2018-09-05 02:05:26");
    TUser user2=new TUser();
    user2.setId("3.456789");
    user2.setName("下雨");
    user2.setDate("2018-09-05 02:05:26");
    TUser user3=new TUser("4.56",null,"2018-09-05 02:05:26");
    TUser user4=new TUser("6.56","kkk","2018-09-05 02:05:26");
    TUser user5=new TUser("55","55","2018-09-05 02:05:26");
    list.add(user);
    list.add(user2);
    list.add(user3);
    list.add(user4);
    list.add(user5);
    String path="d:/excel/";
    String string="2";
    System.out.println(string.indexOf("."));
    exportExcel(listHead,list);
  }

}
class TUser{
  private String id;
  private String name;
  private String date;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public TUser() {
  }

  public TUser(String id, String name, String date) {
    this.id = id;
    this.name = name;
    this.date = date;
  }

  @Override
  public String toString() {
    return "TUser{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", date='" + date + '\'' +
            '}';
  }
}
