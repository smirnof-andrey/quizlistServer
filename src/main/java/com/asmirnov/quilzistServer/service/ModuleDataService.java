package com.asmirnov.quilzistServer.service;

import com.asmirnov.quilzistServer.model.Module;
import com.asmirnov.quilzistServer.model.ModuleAdditionalInfo;
import com.asmirnov.quilzistServer.repository.CardRepo;
import com.asmirnov.quilzistServer.repository.ModuleAdditionalInfoRepo;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

public class ModuleDataService {

    private ModuleAdditionalInfoRepo maiRepo;

    @Autowired
    private CardRepo cardRepo;

    private ModuleAdditionalInfo additionalInfo;

    private Module module;

    public ModuleDataService(Module module, CardRepo cardRepo, ModuleAdditionalInfoRepo maiRepo) {
        this.module = module;
        this.cardRepo = cardRepo;
        this.maiRepo = maiRepo;
        this.additionalInfo = maiRepo.findByModule(module);
        if(additionalInfo == null){
            this.additionalInfo = new ModuleAdditionalInfo(module);
        }
    }

    public void updateModuleInfoItemsCount(){
        additionalInfo.setItemsCount(Math.toIntExact(cardRepo.findByModule(module).size()));
        maiRepo.save(additionalInfo);
    }
}
