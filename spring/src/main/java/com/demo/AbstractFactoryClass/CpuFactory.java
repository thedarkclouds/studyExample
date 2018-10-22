package com.demo.AbstractFactoryClass;

import com.demo.AbstractProductCass.Computer;
import com.demo.AbstractProductCass.Cpu;

/**
 * @author cuishifeng
 * @create 2018-08-02
 **/
public class CpuFactory implements ComputerFactory {

    @Override
    public Computer getProduct() {
        return new Cpu();
    }
}
