package com.asmirnov.quilzistServer.model;

import com.asmirnov.quilzistServer.repository.CardRepo;
import com.asmirnov.quilzistServer.repository.ModuleRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ModuleTest {

    @Autowired
    @Resource
    private ModuleRepo moduleRepo;

    @Autowired
    @Resource
    private CardRepo cardRepo;

    @Test
    public void setItemsCount1() {
        for (Module module : moduleRepo.findAll()) {
            int size = cardRepo.findByModule(module).size();
            System.out.println("Module: "+module.toString()+", items: "+size);
//            Long size2 = (Long) cardRepo.findByModule(module).size();
//            module.setItemsCount((Long) size);
        }
    }
}