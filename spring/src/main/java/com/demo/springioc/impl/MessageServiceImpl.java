package com.demo.springioc.impl;

import com.demo.springioc.face.MessageService;

public class MessageServiceImpl implements MessageService {

    public String getMessage() {
        return "hello world";
    }
}



