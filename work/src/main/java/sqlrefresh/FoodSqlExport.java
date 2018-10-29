package sqlrefresh;

import com.sun.org.apache.bcel.internal.generic.I2F;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;



public class FoodSqlExport {
    private final static String address = "E:\\sql\\foodListUpdate.sql";

    public static void main(String[] args) {

        excelData();
    }


    public static void excelData() {
        try {
            File fo = new File(address);
            if (fo.exists()) {
                fo.delete();
            }
            FileOutputStream out = new FileOutputStream(fo, true);
            ImportExcel importExcel = new ImportExcel();
          //  productRefresh(out, importExcel);
             foodRecommUpdate(out, importExcel);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //生产库数据刷新
    private static void productRefresh(FileOutputStream out, ImportExcel importExcel) {
        // File f = new File("E:\\食品库新增数据");
        File f = new File("F:\\EXCEL2");
        File file1s[] = f.listFiles();
        for (File file : file1s) {
            List<Map<Integer, Object>> lists = importExcel.read(file);
            lists.remove(0);//排除表头
            for (Map<Integer, Object> map : lists) {

              /*  if(map.get(1).toString().contains("莓果草莓谷物香脆早餐麦片")){
                    continue;
                }*/
                refresh(map, out);
            }
        }
    }

    public static void refresh(Map<Integer, Object> map, FileOutputStream out) {
              String name=map.get(1).toString();

              if (name.contains("'")){
                  name=name.replace("'","''");
              }
      /*  if (name.contains("/")){
            name.replace("/","//");
        }*/

        String sql = "";
        sql = "UPDATE T_MGSP_FOOD_LIST "
              /*  "SET QUANTITY=" +quantity.replace("\\b","").replace("  ","") *//*quantity.substring(0,quantity.length()-1)*/
                + "SET WEIGHT=" + map.get(5).toString()
                + ",UNIT=" + map.get(6).toString()
                + ",BASIC_INGREDIENT=" + "'" + getBasicIngredient(map) + "'"
                + ",MICROELEMENT=" + "'" + getMICROELEMENT(map) + "'"
                + " where name=" + "'" + name + "';";
        try {
            out.write(sql.getBytes());
            out.write("\r\n".getBytes());// 写入一个换行
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //食物推荐属性更新
    private static void foodRecommUpdate(FileOutputStream out, ImportExcel importExcel) {
        File f = new File("E:\\excel");
        File file1s[] = f.listFiles();
        for (File file : file1s) {
            List<Map<Integer, Object>> lists = importExcel.read(file);
            for (Map<Integer, Object> map : lists) {
                write(map, out);
            }
        }
    }


    public static void write(Map<Integer, Object> map, FileOutputStream out) {
        String sql = "";
        if (map.size() > 7 && map.size() <= 12) {
            sql = "UPDATE T_MGSP_FOOD_LIST SET RECOMM_WEIGHT=" + map.get(7).toString()
                    + ",IS_BREAKFAST=" + getMealType(map.get(4).toString(), 1)
                    + ",IS_LUNCH=" + getMealType(map.get(4).toString(), 2)
                    + ",IS_SUPPER=" + getMealType(map.get(4).toString(), 3)
                    + ",IS_EXTRA_MEAL=" + getMealType(map.get(4).toString(), 4)
                    + ",IS_MINUS_FAT=" + getTargetOrSeason(map.get(5).toString(), 0)
                    + ",IS_ADD_MUSCLE=" + getTargetOrSeason(map.get(5).toString(), 1)
                    + ",IS_SHAPING=" + getTargetOrSeason(map.get(5).toString(), 2)
                    + ",IS_SPRING=" + getTargetOrSeason(map.get(6).toString(), 0)
                    + ",IS_SUMMER=" + getTargetOrSeason(map.get(6).toString(), 1)
                    + ",IS_AUTUMN=" + getTargetOrSeason(map.get(6).toString(), 2)
                    + ",IS_WINTER=" + getTargetOrSeason(map.get(6).toString(), 3)
                    + ",DIET_CLASSIFY=" + getFoodType(map.get(3).toString())
                    + ",RECOMM_FLAG=1 "
                    + " where name=" + "'" + map.get(1).toString() + "';";
        } else if (map.size() <= 50 && map.size() > 40) {
            sql = "UPDATE T_MGSP_FOOD_LIST SET RECOMM_WEIGHT=" + map.get(6).toString()
                    + ",IS_BREAKFAST=" + getMealType(map.get(2).toString(), 1)
                    + ",IS_LUNCH=" + getMealType(map.get(2).toString(), 2)
                    + ",IS_SUPPER=" + getMealType(map.get(2).toString(), 3)
                    + ",IS_EXTRA_MEAL=" + getMealType(map.get(2).toString(), 4)
                    + ",IS_MINUS_FAT=" + getTargetOrSeason(map.get(3).toString(), 0)
                    + ",IS_ADD_MUSCLE=" + getTargetOrSeason(map.get(3).toString(), 1)
                    + ",IS_SHAPING=" + getTargetOrSeason(map.get(3).toString(), 2)
                    + ",IS_SPRING=" + getTargetOrSeason(map.get(5).toString(), 0)
                    + ",IS_SUMMER=" + getTargetOrSeason(map.get(5).toString(), 1)
                    + ",IS_AUTUMN=" + getTargetOrSeason(map.get(5).toString(), 2)
                    + ",IS_WINTER=" + getTargetOrSeason(map.get(5).toString(), 3)
                    + ",DIET_CLASSIFY=" + getFoodType(map.get(0).toString())
                    + ",RECOMM_FLAG=2 "
                    + " where name=" + "'" + map.get(1).toString() + "';";

        } else {

            return;
        }
        try {
            out.write(sql.getBytes());
            out.write("\r\n".getBytes());// 写入一个换行
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static int getMealType(String mealType, int flag) {
        String meal[] = mealType.split("、");
        List list = Arrays.asList(meal);
        if (flag == 1) {
            if (list.contains("早餐")) {
                return 1;
            }
        }
        if (flag == 2) {
            if (list.contains("午餐")) {
                return 1;
            }
        }
        if (flag == 3) {
            if (list.contains("晚餐")) {
                return 1;
            }
        }
        if (flag == 4) {
            if (list.contains("加餐")) {
                return 1;
            }
        }

        return 0;
    }


    public static int getTargetOrSeason(String target, int flag) {
        //数据[0,1,0]
        target = target.replace("[", "").replace("]", "");
        List<String> list = Arrays.asList(target.split(","));
        if (flag < list.size()) {
            if ("1".equals(list.get(flag))) {
                return 1;
            }
        }
        return 0;
    }

    public static int getFoodType(String type) {
        // 食物类型分类  0:无 1:菜 2：菜汤 3.喝的 4.鸡蛋 5.坚果 6.水果 7.主食
        if ("菜".equals(type)) {
            return 1;
        }
        if ("菜汤".equals(type)) {
            return 2;
        }
        if ("喝的".equals(type)) {
            return 3;
        }
        if ("鸡蛋".equals(type)) {
            return 4;
        }
        if ("坚果".equals(type)) {
            return 5;
        }
        if ("水果".equals(type)) {
            return 6;
        }
        if ("主食".equals(type)) {
            return 7;
        }
        return 0;
    }

    public static String getBasicIngredient(Map<Integer, Object> map) {
        //碳水化合物:0.6克,脂肪:6克,蛋白质:8.7克,纤维素:0.6克
        String basic = "";
        basic = basic + "碳水化合物:" + (map.get(7).toString() != null ? map.get(7).toString() : 0) + "克" + ","
                + "脂肪:" + (map.get(8).toString() != null ? map.get(8).toString() : 0) + "克" + ","
                + "蛋白质:" + (map.get(9).toString() != null ? map.get(9).toString() : 0) + "克" + ","
                + "纤维素:" + (map.get(10).toString() != null ? map.get(10).toString() : 0) + "克" + "";
        return basic;
    }

    //获得微量元素
    public static String getMICROELEMENT(Map<Integer, Object> map) {
        // 维生素A:7.4毫克,维生素C:0.6毫克,维生素E:2.1毫克,胡萝卜素:11.5微克,
        // 硫胺素:0毫克,核黄素:0毫克,烟酸:0.6毫克,胆固醇:18.4毫克,

        // 镁:19.8毫克,钙:34.1毫克,铁:0.8毫克,锌:0.6毫克,铜:0.1毫克,
        // 锰:0.3毫克,钾:109.3毫克,磷:74.9毫克,钠:170.2毫克,硒:3.6
        String basic = "";
        basic = basic + "维生素A:" + (map.get(7).toString() != null ? map.get(11).toString() : 0) + "毫克" + ","
                + "维生素C:" + (map.get(8).toString() != null ? map.get(12).toString() : 0) + "毫克" + ","
                + "维生素E:" + (map.get(8).toString() != null ? map.get(13).toString() : 0) + "毫克" + ","
                + "胡萝卜素:" + (map.get(8).toString() != null ? map.get(14).toString() : 0) + "微克" + ","
                + "硫胺素:" + (map.get(8).toString() != null ? map.get(15).toString() : 0) + "毫克" + ","
                + "核黄素:" + (map.get(8).toString() != null ? map.get(16).toString() : 0) + "毫克" + ","
                + "烟酸:" + (map.get(8).toString() != null ? map.get(17).toString() : 0) + "毫克" + ","
                + "胆固醇:" + (map.get(8).toString() != null ? map.get(18).toString() : 0) + "毫克" + ","
                + "镁:" + (map.get(8).toString() != null ? map.get(19).toString() : 0) + "毫克" + ","
                + "钙:" + (map.get(8).toString() != null ? map.get(20).toString() : 0) + "毫克" + ","
                + "铁:" + (map.get(8).toString() != null ? map.get(21).toString() : 0) + "毫克" + ","
                + "锌:" + (map.get(8).toString() != null ? map.get(22).toString() : 0) + "毫克" + ","
                + "铜:" + (map.get(8).toString() != null ? map.get(23).toString() : 0) + "毫克" + ","
                + "锰:" + (map.get(8).toString() != null ? map.get(24).toString() : 0) + "毫克" + ","
                + "钾:" + (map.get(8).toString() != null ? map.get(25).toString() : 0) + "毫克" + ","
                + "磷:" + (map.get(8).toString() != null ? map.get(26).toString() : 0) + "毫克" + ","
                + "钠:" + (map.get(8).toString() != null ? map.get(27).toString() : 0) + "毫克" + ","
                + "硒:" + (map.get(8).toString() != null ? map.get(28).toString() : 0) + "毫克" + "";
        return basic;
    }


}
