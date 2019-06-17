package com.asmirnov.quilzistServer.controller.rest;

import com.asmirnov.quilzistServer.model.Module;
import com.asmirnov.quilzistServer.model.ModuleAdditionalInfo;
import com.asmirnov.quilzistServer.service.ModuleCardRestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("module")
public class ModuleRestController {

    private final ModuleCardRestService mcRestService;

    public ModuleRestController(ModuleCardRestService mcRestService) {
        this.mcRestService = mcRestService;
    }

    @GetMapping
    public List<ModuleAdditionalInfo> getUserModules() {
        return mcRestService.getUserModules();
    }

    @GetMapping("{id}")
    public Module getModuleById(@PathVariable("id") Module module) {
        return module;
    }

    @PostMapping
    public Module createModule(@RequestBody Module module) {
        return mcRestService.createModule(module);
    }

    @PutMapping("{id}")
    public Module updateModule(@PathVariable("id") Module moduleFromDB,
            @RequestBody Module module){
        return mcRestService.updateModule(moduleFromDB,module);
    }

    @DeleteMapping("{id}")
    public void deleteModule(@PathVariable("id") Module module){
        mcRestService.deleteModule(module);
    }

}
