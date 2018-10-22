package com.demo.AbstractFactoryClass;

import com.demo.AbstractProductCass.Computer;

public interface ComputerFactory {
/*
    优点

            代码解耦
    实现多个产品族(相关联产品组成的家族)，而工厂方法模式的单个产品,可以满足更多的生产需求
            很好的满足OCP开放封闭原则
    抽象工厂模式中我们可以定义实现不止一个接口，一个工厂也可以生成不止一个产品类 对于复杂对象的生产相当灵活易扩展

    缺点
1.扩展产品族相当麻烦 而且扩展产品族会违反OCP,因为要修改所有的工厂，例如我们有电脑和鼠标组成的一个产品族，我们写完代码再去添加一个键盘就会很麻烦，看完下面代码就会理解了
2.由于抽象工厂模式是工厂方法模式的扩展 总体的来说 很笨重
*/




    /**
     * 工厂方法模式的抽象工厂
     * @return
     */
    Computer getProduct();
}
