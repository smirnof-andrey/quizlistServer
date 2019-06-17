package com.asmirnov.quilzistServer.service;

import com.asmirnov.quilzistServer.model.Module;
import com.asmirnov.quilzistServer.model.ModuleAdditionalInfo;
import com.asmirnov.quilzistServer.repository.ModuleAdditionalInfoRepo;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class ModuleInfoService {

    @Autowired
    private ModuleAdditionalInfoRepo maiRepo;

    private ModuleAdditionalInfo additionalInfo;

    public void updateModuleInfoItemsCount(Module module, Integer itemsCount){

        this.additionalInfo = maiRepo.findByModule(module);
        if(additionalInfo == null){
            this.additionalInfo = new ModuleAdditionalInfo(module);
        }
        additionalInfo.setItemsCount(itemsCount);
        maiRepo.save(additionalInfo);
    }

}
