package com.asmirnov.quilzistServer.controller;

import com.asmirnov.quilzistServer.domain.Module;
import com.asmirnov.quilzistServer.domain.User;
import com.asmirnov.quilzistServer.repository.ModuleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MainController {

    private User currentUser;

    @Autowired
    private ModuleRepo moduleRepo;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@AuthenticationPrincipal User user,Map<String, Object> model){
        currentUser = user;
        model.put("currentUsername",currentUser.getUsername());

        Iterable<Module> modules = moduleRepo.findByAuthor(currentUser);
        model.put("modules", modules);
        return "main";
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String name,
            @RequestParam String info, Map<String, Object> model){
        Module module = new Module(name, info, user);
        moduleRepo.save(module);

        Iterable<Module> modules = moduleRepo.findByAuthor(currentUser);
        model.put("modules", modules);

        model.put("currentUsername",currentUser.getUsername());

        return "main";
    }

    @PostMapping("/filter")
    public String filter(@RequestParam String filter, Map<String, Object> model2){
        Iterable<Module> modules;
        if(filter != null && !filter.isEmpty()){
            modules = moduleRepo.findByAuthorAndName(currentUser,filter);
        }
        else{
            modules = moduleRepo.findByAuthor(currentUser);
        }

        model2.put("modules",modules);
        model2.put("currentUsername",currentUser.getUsername());

        return "main";
    }

}
