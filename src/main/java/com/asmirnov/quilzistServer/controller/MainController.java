package com.asmirnov.quilzistServer.controller;

import com.asmirnov.quilzistServer.model.Module;
import com.asmirnov.quilzistServer.model.User;
import com.asmirnov.quilzistServer.repository.ModuleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private ModuleRepo moduleRepo;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@AuthenticationPrincipal User user,Map<String, Object> model){

        refreshPublicInfo(model);

        return "main";
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String name,
            @RequestParam String info, Map<String, Object> model){
        Module module = new Module(name, info, user);
        moduleRepo.save(module);

        refreshPublicInfo(model);

        return "main";
    }

    @PostMapping("/filter")
    public String filter(@RequestParam String filter, Map<String, Object> model2){

        refreshPublicInfo(model2, filter);

        return "main";
    }

    private void refreshPublicInfo(Map<String, Object> model){
        refreshPublicInfo(model, null);
    }

    private void refreshPublicInfo(Map<String, Object> model, String filter) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        model.put("currentUsername",user.getUsername());

        model.put("roles",user.getRoles());

        Iterable<Module> modules;
        if(filter != null && !filter.isEmpty()){
            modules = moduleRepo.findByAuthorAndName(user,filter);
        }
        else{
            modules = moduleRepo.findByAuthor(user);
        }

        model.put("modules",modules);

    }

}
