package sqlexport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FoodSqlExport {
    private final  static String address="E:\\sql\\foodListUpdate.sql";
    public static void main(String[] args) {

        excelData();
    }

    public static void excelData() {
        try {
        File fo=    new File(address);
          if (fo.exists()){
              fo.delete();
          }
            FileOutputStream out = new FileOutputStream(fo,true);
            ImportExcel importExcel = new ImportExcel();
            File f = new File("E:\\excel");
            File file1s[] = f.listFiles();
            for (File file : file1s) {
                List<Map<Integer, Object>> lists = importExcel.read(file);
                for (Map<Integer, Object> map : lists) {
                    write(map,out);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void write(Map<Integer, Object> map, FileOutputStream out) {
        String sql = "";
        if (map.size() > 7&&map.size() <=12) {
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
                    +",RECOMM_FLAG=1 "
                    + " where name=" + "'" + map.get(1).toString() + "';";
        }else if (map.size() <=50&&map.size() >40){
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
                    +",RECOMM_FLAG=2 "
                    + " where name=" + "'" + map.get(1).toString() + "';";

        }else {

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


}
