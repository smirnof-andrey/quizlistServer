package com.asmirnov.quilzistServer.controller.rest;

import com.asmirnov.quilzistServer.model.Card;
import com.asmirnov.quilzistServer.model.Module;
import com.asmirnov.quilzistServer.model.User;
import com.asmirnov.quilzistServer.Views;
import com.asmirnov.quilzistServer.repository.CardRepo;
import com.asmirnov.quilzistServer.repository.ModuleRepo;
import com.asmirnov.quilzistServer.repository.UserRepo;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("module")
public class ModuleRestController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModuleRepo moduleRepo;

    @Autowired
    private CardRepo cardRepo;

    @GetMapping
    //@JsonView(Views.FullData.class)
    public List<Module> getUserModules() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return moduleRepo.findByAuthor(user);
    }

    @GetMapping("{id}")
    //@JsonView(Views.FullData.class)
    public Module getModuleById(@PathVariable("id") Module module) {
        return module;
    }

    @DeleteMapping("{id}")
    @JsonView(Views.Info.class)
    public void deleteModule(@PathVariable("id") Module module){
        List<Card> cardList = cardRepo.findByModule(module);
        cardRepo.deleteAll(cardList);
        moduleRepo.delete(module);
    }

    @PostMapping
    public Module createModule(@RequestBody Module module) {
        //if (module.getAuthor() == null){
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            module.setAuthor(user);
        //}
        return moduleRepo.save(module);
    }

    @PutMapping("{id}")
    public Module updateModule(
            @PathVariable("id") Module moduleFromDB,
            @RequestBody Module module){
        BeanUtils.copyProperties(module,moduleFromDB,"id","author");
        return moduleRepo.save(moduleFromDB);
    }

//    @GetMapping("{id}/cards")
//    //@JsonView(Views.FullData.class)
//    public List<Card> getCardsByModule(@PathVariable("id") Module module) {
//        return cardRepo.findByModule(module);
//    }
}
