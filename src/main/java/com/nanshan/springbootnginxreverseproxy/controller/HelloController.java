package com.nanshan.springbootnginxreverseproxy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @author RogerLo
 * @date 2023/6/11
 */
@Controller
public class HelloController {

    // GET: http://localhost:8080/RogerReverseProxyTest/toHelloJsp?empName=Roger
    @RequestMapping(value="/toHelloJsp", method = RequestMethod.GET)
    public ModelAndView hello(@RequestParam Map<String, Object> model) {
        System.out.println("model = " + model);
        model.put("message", model.get("empName"));
        return new ModelAndView("Hello_1", model); // mapping 到 /WEB-INF/jsp/hello_1.jsp
    }

}
