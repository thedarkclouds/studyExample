package com.demo;

import com.pojo.Persion;

import java.io.*;

public class SeralizeDemo {

    public static void main(String[] args) {
        SerializeDemo();
        falseSerializeDemo();
    }

    public static  void SerializeDemo(){
        try{
            ObjectOutputStream oo=new ObjectOutputStream(new FileOutputStream(new File("persion")));
            Persion persion=new Persion();
            persion.setAge(18);
            persion.setName("huyue");
            oo.writeObject(persion);
            System.out.println("序列化成功");
            oo.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static  void falseSerializeDemo(){
        ObjectInputStream obj = null;
        try{
            obj=new ObjectInputStream(new FileInputStream(new File("persion")));

       Persion persion=(Persion)obj.readObject();
            System.out.println("反序列化=="+persion);
        }catch (Exception e){

        }



    }
}
