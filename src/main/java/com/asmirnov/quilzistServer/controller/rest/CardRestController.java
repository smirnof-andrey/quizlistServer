package com.asmirnov.quilzistServer.controller.rest;

import com.asmirnov.quilzistServer.model.Card;
import com.asmirnov.quilzistServer.model.Module;
import com.asmirnov.quilzistServer.model.User;
import com.asmirnov.quilzistServer.repository.CardRepo;
import com.asmirnov.quilzistServer.repository.ModuleAdditionalInfoRepo;
import com.asmirnov.quilzistServer.repository.ModuleRepo;
import com.asmirnov.quilzistServer.repository.UserRepo;
import com.asmirnov.quilzistServer.service.ModuleDataService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("cards")
public class CardRestController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModuleRepo moduleRepo;

    @Autowired
    private CardRepo cardRepo;

    @Autowired
    private ModuleAdditionalInfoRepo maiRepo;

    @GetMapping("{id}")
    //@JsonView(Views.FullData.class)
    public List<Card> getCardsByModule(@PathVariable("id") Module module) {
        return cardRepo.findByModule(module);
    }


    @PostMapping("{id}")
    public Card createCard(@PathVariable("id") Module module, @RequestBody Card card) {
        card.setModule(module);
        Card newCard = cardRepo.save(card);
        log.debug("create new card: {}",newCard.toString());
        return newCard;
    }

    @PostMapping
    public String createModuleCards(@RequestBody Map<String, Object> map){

        String errorMessage = "";

        ObjectMapper mapper = new ObjectMapper();
        Module module;
        List<Card> cardsList;

        try {
            module = mapper.convertValue(map.get("module"), Module.class);
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(moduleRepo.findByAuthorAndName(user, module.getName()).isEmpty()){
                module.setAuthor(user);
                module = moduleRepo.save(module);
                log.debug("create new: {}",module);
            }else{
                return "module is exist";
            }
        }catch(Exception e){
            e.printStackTrace();
            errorMessage = e.getMessage();
            return errorMessage;
        }

        try {
            cardsList = mapper.convertValue(map.get("cardsList"), new TypeReference<List<Card>>(){});

            for (Card card : cardsList) {
                Card newCard = createCard(module,card);
                card.setId(newCard.getId());
            }
        }catch(Exception e){
            e.printStackTrace();
            errorMessage = e.getMessage();
        }
        return errorMessage;
    }


    @PutMapping("card/{id}")
    public Card updateCard(@PathVariable("id") Card cardFromDB, @RequestBody Card card){
        BeanUtils.copyProperties(card,cardFromDB,"id");
        return cardRepo.save(cardFromDB);
    }

    @PutMapping("{id}")
    public String updateModuleCards(@PathVariable("id") Module moduleFromDB, @RequestBody Map<String, Object> map){

        String errorMessage = "";

        ObjectMapper mapper = new ObjectMapper();
        Module module;
        List<Card> cardsList;
        try {
            module = mapper.convertValue(map.get("module"), Module.class);
            System.out.println(module);

            if(!moduleFromDB.equals(module)) {
                BeanUtils.copyProperties(module, moduleFromDB, "id", "author");
                moduleRepo.save(moduleFromDB);
            }
        }catch(Exception e){
            e.printStackTrace();
            errorMessage =e.getMessage();
            return errorMessage;
        }

        try {
            cardsList = mapper.convertValue(map.get("cardsList"), new TypeReference<List<Card>>(){});
            System.out.println(cardsList);

            updateCards(module, cardsList);

        }catch(Exception e){
            e.printStackTrace();
            errorMessage =e.getMessage();
        }
        return errorMessage;
    }

    private void updateCards(Module module, List<Card> cardsList) {

        Card cardFromDB;

        for (Card card : cardsList) {
            int cardId = card.getId();
            if(cardRepo.existsById(cardId)) {
                cardFromDB = cardRepo.findById(cardId).get();
                if(!cardFromDB.equals(card)){
                    updateCard(cardFromDB,card);
                }
            }else{
                Card newCard = createCard(module,card);
                card.setId(newCard.getId());
            }
        }
        List cardsToDelete = cardRepo.findByModule(module);
        cardsToDelete.removeAll(cardsList);
        cardRepo.deleteAll(cardsToDelete);

        new ModuleDataService(module, cardRepo, maiRepo).updateModuleInfoItemsCount();
    }


    @DeleteMapping("{id}")
    public void deleteCard(@PathVariable("id") Card card){
        cardRepo.delete(card);
    }

}
