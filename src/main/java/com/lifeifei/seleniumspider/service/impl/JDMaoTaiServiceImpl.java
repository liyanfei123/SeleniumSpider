package com.lifeifei.seleniumspider.service.impl;

import com.lifeifei.seleniumspider.service.JDMaoTaiService;
import com.lifeifei.seleniumspider.ui.core.config.BrowserConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JDMaoTaiServiceImpl implements JDMaoTaiService {

    @Autowired
    private BrowserConfig browserConfig;

    @Override
    public Boolean execute() {
        return null;
    }


}
