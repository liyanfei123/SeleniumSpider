package com.lifeifei.seleniumspider.taobao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lifeifei.seleniumspider.service.MaoTaiService;

@Slf4j
@RestController
@RequestMapping("/taobao/maotai")
public class MaoTaiController {

    @Autowired
    private MaoTaiService maoTaiService;

    @GetMapping("/execute")
    public void execute() {
        maoTaiService.taoBaoExecute();
    }
}
