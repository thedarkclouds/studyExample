package com.demo.springioc;

import com.demo.springioc.face.MessageService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;

public class App {

    //1.spring-context 会自动将 spring-core、spring-beans、spring-aop、spring-expression 这几个基础 jar 包带进来。
    //2.ApplicationContext 启动过程中，会负责创建实例 Bean，往各个 Bean 中注入依赖等。
    //3.BeanFactory，从名字上也很好理解，生产 bean 的工厂，它负责生产和管理各个 bean 实例。


    public static void main(String[] args) {
        // 用我们的配置文件来启动一个 ApplicationContext
        String path = "classpath:application.xml;application2.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:application.xml");


        String paths[] = StringUtils.tokenizeToStringArray(path, ",; \t\n");
        System.out.println(paths);
        System.out.println("context 启动成功");

        // 从 context 中取出我们的 Bean，而不是用 new MessageServiceImpl() 这种方式
        MessageService messageService = context.getBean(MessageService.class);
        // 这句将输出: hello world
        System.out.println(messageService.getMessage());

    }


}
