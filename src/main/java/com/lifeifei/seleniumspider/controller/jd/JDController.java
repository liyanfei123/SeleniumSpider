package com.lifeifei.seleniumspider.controller.jd;

import com.lifeifei.seleniumspider.service.JDMaoTaiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/jd/maotai")
public class JDController {

    @Autowired
    private JDMaoTaiService jdMaoTaiService;

    @GetMapping("/execute")
    public void execute() {
        jdMaoTaiService.execute();
    }
}
