package com.lifeifei.seleniumspider.controller.yanxuan;

import com.lifeifei.seleniumspider.service.TBMaoTaiService;
import com.lifeifei.seleniumspider.service.YanXuanMaoTaiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/yanxuan/maotai")
public class YanXuanController {

    @Autowired
    private YanXuanMaoTaiService yanXuanMaoTaiService;

    @GetMapping("/execute")
    public void execute() {
        yanXuanMaoTaiService.execute();
    }
}
