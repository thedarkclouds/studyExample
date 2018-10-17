package com.test;

import java.util.HashMap;
import java.util.Random;

public class 编程题 {

    public static void main(String[] args) {

        test1();//第一题
        //test2();//第二题
        //  HashMap hashMap=new HashMap();
        //  hashMap.put("kk","kk");
        // System.out.println("请输入月份，和座舱级别，true代表头等舱，false代表经济舱");
        //  test3(10,false);

      // test4();
        test5();
    }

  /*  第一题：分析以下需求，并用代码实现（15分）：
    定义一个int类型变量初始化值为2-100之间任意一个整数，
    求2到该变量之间的偶数个数及奇数的和，并将结果打印到控制台上*/

    public static void test1() {
        //定义一个int类型变量初始化值为2-100之间任意一个整数，
        Random ra = new Random();
        int num = ra.nextInt(100) + 2;
        ;
        System.out.println("此次随机变量为==" + num);
        // 求2到该变量之间的偶数个数及奇数的和，并将结果打印到控制台上
        int a = 0;   //接受收偶数个数
        int b = 0;    //接受奇数和
        while (true) {
            if (num % 2 == 0) {
                a++;
            } else {
                b = num + b;
            }
            num--;//递减，一直到num<2时结束
            if (num < 2) {
                break;
            }

        }
        System.out.println("偶数个数==" + a + "    奇数和===" + b);

    }


    /* 打印1到100之内的整数，但数字中包含9的要跳过，
    中间用逗号分隔，统计满足条件的数字的个数并打印*/

    public static void test2() {
        int i = 0;    //1到100之内得整数从0 开始递增加
        int num = 0; //统计字中不包含9得个数
        String temp = "";
        while (true) {
            i++;
            if (i % 10 == 9) {
                continue;//跳过本次循环
            } else {
                num++;
                temp = temp + i + ",";
            }
            if (i == 100) {
                break;//结束循环
            }
        }
        System.out.println("统计字中不包含9得个数" + num);
        System.out.println("足条件的数字" + temp);
    }

    /*   第三题:分析以下需求，并用代码实现(20分)：
               1.某人准备去海南旅游，现在要订购机票。机票的价格受季节旺季、淡季的影响，头等舱和经济舱价格也不同
               2.假设机票原价假设为5000元（自定义变量），4-10月为旺季，旺季头等舱打9折，经济舱打8折，淡季头等舱打5折，经济舱打4折。
               3.编写程序，使用嵌套if选择结构根据出行的月份和选择的仓位输出实际的机票价格*/
    public static void test3(int month, Boolean flag) {
        //如果flag==true 表示 头等舱
        //如果flag==false 表示 经济舱

        boolean sign = true;  //标记淡季和旺季  ，用于输出结果  true 表示默认旺季
        if (month >= 1 && month <= 12) {

            System.out.println("月份满足条件");
        } else {
            System.out.println("请输入正确月份1-12之间");
        }

        int RMB = 5000;
        double seatCash = 0;  //头等舱


        //大于等于4，小于等于10的月份
        if (month >= 4 && month <= 10) {

            if (flag) {
                seatCash = RMB * 0.9;
            } else {
                seatCash = RMB * 0.8;
            }

        } else {
            sign = false;
            if (flag) {
                seatCash = RMB * 0.5;
            } else {
                seatCash = RMB * 0.4;
            }
        }

        //此处运用了三元运算符
        System.out.println("当前月是" + month + ",是" + (sign ? "旺季" : "淡季") + "，你选择的是" + (flag ? "头等舱" : "经济舱") + ",价格是 ：" + seatCash);
    }

    public static void test4() {
/*
        第三题：分析以下需求并实现
        1.打印1到100之内的整数，但数字中包含9的要跳过
        2.每行输出5个满足条件的数，之间用空格分隔
        3.如：1 2 3 4 5*/

        String ss = "";
        int j = 0;
        for (int i = 1; i <= 100; i++) {
            if (i % 10 == 9) {
                continue;

            } else {
                j++;
                ss = ss + i + ",";
                if (j % 5 == 0) {
                    System.out.println(ss);
                    ss = "";
                }
            }
        }
    }

    public static void test5(){

      /*  第五题：分析以下需求并实现
        1.使用嵌套循环完成99乘法表的打印
        1*1=1
        1*2=2	2*2=4
        1*3=3	2*3=6	3*3=9*/
      String ss="";
      for (int i =1;i<=9;i++){

          for (int j =1;j<=i;j++){
              ss=ss+i+"*"+j+"   ";
              if (j==i){
                  System.out.println(ss);
                  ss="";
              }
          }
      }
    }


}
