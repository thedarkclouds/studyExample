package com.demo.AbstractFactoryClass;

import com.demo.AbstractProductCass.Computer;
import com.demo.AbstractProductCass.Disk;

/**
 * @author cuishifeng
 * @create 2018-08-02
 **/
public class DiskFactory implements ComputerFactory{
    @Override
    public Computer getProduct() {
        return new Disk();
    }
}

