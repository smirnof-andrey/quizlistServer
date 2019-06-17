package com.asmirnov.quilzistServer.service;

import com.asmirnov.quilzistServer.dto.ModuleCardsDTO;
import com.asmirnov.quilzistServer.model.Module;
import com.asmirnov.quilzistServer.model.ModuleAdditionalInfo;
import com.asmirnov.quilzistServer.model.User;
import com.asmirnov.quilzistServer.repository.ModuleAdditionalInfoRepo;
import com.asmirnov.quilzistServer.repository.ModuleRepo;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
@Slf4j
public class ModuleService {

    @Autowired
    private ModuleRepo moduleRepo;

    @Autowired
    private ModuleAdditionalInfoRepo maiRepo;

    @Autowired
    private ModuleCardRestService mcRestService;

    public List<ModuleAdditionalInfo> getModulesByCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Module> moduleList = moduleRepo.findByAuthor(user);
        List<ModuleAdditionalInfo> mai = maiRepo.findByModuleList(moduleList);
        log.debug("getModulesByCurrentUser({}) : {}", user.getUsername(), mai.toString());
        return mai;
    }

    public ModuleCardsDTO createModule(Module module) {
        ModuleCardsDTO requestDTO = new ModuleCardsDTO();

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            if(moduleRepo.findByAuthorAndName(user, module.getName()).isEmpty()){
                module.setAuthor(user);
                module = moduleRepo.save(module);
                requestDTO.setModule(module);
                requestDTO.setResponseCode(0);
            }else{
                requestDTO.setResponseCode(11);
            }
        }catch(Exception e){
            e.printStackTrace();
            requestDTO.setResponseCode(99, e.getMessage());
        }

        log.debug("createModule:{} {}", requestDTO.getErrorInfo(), module);
        return requestDTO;
    }

    public Module createModuleNoDTO(Module module) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        module.setAuthor(user);
        log.debug("createModule: {}", module.toString());
        return moduleRepo.save(module);
    }

    public Module updateModule(Module moduleFromDB, Module module) {
        log.debug("updateModule, before: {}", moduleFromDB.toString());
        BeanUtils.copyProperties(module,moduleFromDB,"id","author");
        moduleFromDB = moduleRepo.save(moduleFromDB);
        log.debug("updateModule, after: {}", moduleFromDB.toString());
        return moduleRepo.save(moduleFromDB);
    }

    public void deleteModule(Module module) {
        log.debug("deleteModule: {}", module.toString());
        moduleRepo.delete(module);
    }
}
