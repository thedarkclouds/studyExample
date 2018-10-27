package IO;


import java.io.*;

/*
 * 文件编码转换
 * */
public class EncodingConvert {


    public static void main(String[] args) {
        InputStreamReader isr = null;
        OutputStreamWriter isw = null;
        File file = new File("H:\\视频\\JAVA线程并发教程\\源代码与资料\\ThreadTest2");
        try {
            File files[] = file.listFiles();
            for (File f : files) {
                encoding(f);

            }
        } catch (Exception e) {
            e.printStackTrace();

        }


    }

    private static void encoding(File file) throws IOException {


        if (file.isDirectory()) {
            System.out.println(file.getName());
            File files[] = file.listFiles();
            for (File f : files) {
                System.out.println(f.getName());
                encoding(f);
            }
        }

        if (!suffix(file)) {
            return;
        }
        InputStreamReader     isr = new InputStreamReader(new FileInputStream(file), "gbk");
        OutputStreamWriter isw = new OutputStreamWriter(new FileOutputStream("F:\\Thread\\"+file.getName()), "utf-8");
        int len = 0;
        while ((len = isr.read()) != -1) {
            isw.write(len);
        }
        isr.close();
        isw.close();
    }


    public static Boolean suffix(File file) {
        Boolean flag = false;
        String fileName = file.getName();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);

        if (suffix.equals("java")) {
            flag = true;
        }
        return flag;
    }
}

