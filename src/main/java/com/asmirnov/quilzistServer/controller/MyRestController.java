package com.asmirnov.quilzistServer.controller;

import com.asmirnov.quilzistServer.model.Module;
import com.asmirnov.quilzistServer.model.User;
import com.asmirnov.quilzistServer.model.Views;
import com.asmirnov.quilzistServer.repository.ModuleRepo;
import com.asmirnov.quilzistServer.repository.UserRepo;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MyRestController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModuleRepo moduleRepo;

//    @GetMapping("/userModules")
//    @JsonView(Views.Info.class)
//    public List<Module> userModules(@RequestParam(value="user_id", defaultValue="21") Long user_id) {
//        User user = userRepo.findById((Long) user_id).get();
//        return moduleRepo.findByAuthor(user);
//    }

    @GetMapping("/getUserModules/{id}")
    @JsonView(Views.Info.class)
    public List<Module> getUserModules(@PathVariable("id") User user) {
        return moduleRepo.findByAuthor(user);
    }

    @PostMapping("/createModule")
    public Module createModule(@RequestBody Module module) {
        return moduleRepo.save(module);
    }

//    @PutMapping("{id}")
//    public Module updateModule(
//            @PathVariable("id") Module moduleFromDB,
//            @RequestBody Module module){
//        BeanUtils.copyProperties(module,moduleFromDB,"id");
//        return moduleRepo.save(moduleFromDB);
//    }
//
//    @DeleteMapping("{id}")
//    public void deleteModule(@PathVariable("id") Module module){
//        moduleRepo.delete(module);
//    }
}
