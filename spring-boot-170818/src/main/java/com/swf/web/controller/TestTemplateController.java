package com.swf.web.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestTemplateController {

	/**
     * 返回html模板.
     */
    @RequestMapping("/helloHtml")
    public String helloHtml(Map<String,Object> map){
       map.put("hello","from TemplateController.helloHtml");
       return"/test/helloHtml";
    }
    
    /**
     * 返回html模板.
     */
    @RequestMapping("/helloFtl")
    public String helloFtl(Map<String,Object> map){
       map.put("hello","from TemplateController.helloFtl");
       return"/test/helloFtl";
    }
}
