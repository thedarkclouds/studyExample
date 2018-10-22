package com.demo.AbstractFactoryClass;

import com.demo.AbstractProductCass.Computer;
import com.demo.AbstractProductCass.Displayer;

/**
 * @author cuishifeng
 * @create 2018-08-02
 **/
public class DisplayerFactory implements ComputerFactory {
    @Override
    public Computer getProduct() {
        return new Displayer();
    }
}
