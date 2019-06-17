package com.asmirnov.quilzistServer.controller.rest;

import com.asmirnov.quilzistServer.dto.ModuleCardsDTO;
import com.asmirnov.quilzistServer.model.Card;
import com.asmirnov.quilzistServer.model.Module;
import com.asmirnov.quilzistServer.service.ModuleCardRestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cards")
public class CardRestController {

    private final ModuleCardRestService mcRestService;

    public CardRestController(ModuleCardRestService mcRestService) {
        this.mcRestService = mcRestService;
    }

    @GetMapping("{id}")
    public List<Card> getCardsByModule(@PathVariable("id") Module module) {
        return mcRestService.getCardsByModule(module);
    }

    @PostMapping("{id}")
    public Card createCard(@PathVariable("id") Module module, @RequestBody Card card) {
        return mcRestService.createCard(module,card);
    }

    @PostMapping
    public ModuleCardsDTO createModuleCards(@RequestBody ModuleCardsDTO requestDTO){

        return mcRestService.createModuleCards(requestDTO);
    }

    @PutMapping("card/{id}")
    public Card updateCard(@PathVariable("id") Card cardFromDB, @RequestBody Card card){
        return mcRestService.updateCard(cardFromDB,card);
    }

    @PutMapping("{id}")
    public ModuleCardsDTO updateModuleCards(@PathVariable("id") Module moduleFromDB, @RequestBody ModuleCardsDTO requestDTO){
        return mcRestService.updateModuleCards(moduleFromDB, requestDTO);
    }

    @DeleteMapping("{id}")
    public void deleteCard(@PathVariable("id") Card card){
        mcRestService.deleteCard(card);
    }

}
