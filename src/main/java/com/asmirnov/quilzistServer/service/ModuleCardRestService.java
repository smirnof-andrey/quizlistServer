package com.asmirnov.quilzistServer.service;

import com.asmirnov.quilzistServer.dto.ModuleCardsDTO;
import com.asmirnov.quilzistServer.model.Card;
import com.asmirnov.quilzistServer.model.Module;
import com.asmirnov.quilzistServer.model.ModuleAdditionalInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModuleCardRestService {

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private CardService cardService;

    private ModuleCardsDTO responseDTO;

    public ModuleCardRestService() {
        this.responseDTO = new ModuleCardsDTO();
    }

    public List<ModuleAdditionalInfo> getUserModules() {
        List<ModuleAdditionalInfo> mai = new ArrayList<>();
        try {
            mai = moduleService.getModulesByCurrentUser();

        }catch(Exception e){
//            responseDTO.setResponseCode(99,e.getMessage());
        }
        return mai;
    }

    public Module createModule(Module module) {
        return moduleService.createModuleNoDTO(module);
    }

    public Module updateModule(Module moduleFromDB, Module module) {
        return moduleService.updateModule(moduleFromDB, module);
    }

    public void deleteModule(Module module) {
        cardService.deleteByModule(module);
        moduleService.deleteModule(module);
    }


    public List<Card> getCardsByModule(Module module) {
        return cardService.getCardsByModule(module);
    }

    public void deleteCard(Card card) {
        cardService.deleteCard(card);
    }

    public Card createCard(Module module, Card card) {
        return cardService.createCard(module,card);
    }

    public ModuleCardsDTO createModuleCards(ModuleCardsDTO requestDTO) {

        Module module = requestDTO.getModule();
        responseDTO = moduleService.createModule(module);
        if(responseDTO.getResponseCode() == 0) {
            responseDTO = cardService.createCards(module, requestDTO.getCardList());
        }

        return responseDTO;
    }

    public Card updateCard(Card cardFromDB, Card card) {
        return cardService.updateCard(cardFromDB, card);
    }

    public ModuleCardsDTO updateModuleCards(Module moduleFromDB, ModuleCardsDTO requestDTO) {
        Module module = requestDTO.getModule();
        if(!moduleFromDB.equals(module)) {
            moduleService.updateModule(moduleFromDB, module);
        }
        responseDTO = cardService.updateCards(module, requestDTO.getCardList());

        return responseDTO;
    }
}
