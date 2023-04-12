package com.lifeifei.seleniumspider.controller.taobao;

import com.lifeifei.seleniumspider.service.TBMaoTaiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/taobao/maotai")
public class MaoTaiController {

    @Autowired
    private TBMaoTaiService tbMaoTaiService;

    @GetMapping("/execute")
    public void execute() {
        tbMaoTaiService.taoBaoExecute();
    }
}
