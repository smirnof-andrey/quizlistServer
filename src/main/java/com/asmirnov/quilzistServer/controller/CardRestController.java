package com.asmirnov.quilzistServer.controller;

import com.asmirnov.quilzistServer.model.Card;
import com.asmirnov.quilzistServer.model.Module;
import com.asmirnov.quilzistServer.model.User;
import com.asmirnov.quilzistServer.model.Views;
import com.asmirnov.quilzistServer.repository.CardRepo;
import com.asmirnov.quilzistServer.repository.ModuleRepo;
import com.asmirnov.quilzistServer.repository.UserRepo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("cards")
public class CardRestController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModuleRepo moduleRepo;

    @Autowired
    private CardRepo cardRepo;

    @GetMapping("{id}")
    //@JsonView(Views.FullData.class)
    public List<Card> getCardsByModule(@PathVariable("id") Module module) {
        return cardRepo.findByModule(module);
    }

    @PostMapping("{id}")
    public Card createCard(@PathVariable("id") Module module, @RequestBody Card card) {
        card.setModule(module);
        card.setId(1);  // bad hardcode fix( I have to solve this problem later
        return cardRepo.save(card);
    }

    @PutMapping("card/{id}")
    public Card updateCard(
            @PathVariable("id") Card cardFromDB,
            @RequestBody Card card){
        BeanUtils.copyProperties(card,cardFromDB,"id");
        return cardRepo.save(cardFromDB);
    }

    @PutMapping("{id}")
    public Module updateModuleCards(
            @PathVariable("id") Module moduleFromDB,
            @RequestBody Map<String, Object> map){

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
            module = new Module("new module","info1",new User());
        }

        try {
            cardsList = mapper.convertValue(map.get("cardsList"), new TypeReference<List<Card>>(){});
            System.out.println(cardsList);

            updateCards(module, cardsList);

        }catch(Exception e){
            e.printStackTrace();
        }

        return module;
    }

    private void updateCards(Module module, List<Card> cardsList) {

        Card cardFromDB;
        List cardsToUpadate = new ArrayList<Card>();
        List cardsToCreate = new ArrayList<Card>();

        for (Card card : cardsList) {
            int cardId = card.getId();
            if(cardRepo.existsById(cardId)) {
                cardFromDB = cardRepo.findById(cardId).get();
                if(!cardFromDB.equals(card)){
                    updateCard(cardFromDB,card);
                }
            }else{
//                createCard(module,card);
            }
        }
        List cardsToDelete = cardRepo.findByModule(module);
        cardsToDelete.removeAll(cardsList);
//        cardRepo.deleteAll(cardsToDelete);
    }


    @DeleteMapping("{id}")
    public void deleteCard(@PathVariable("id") Card card){
        cardRepo.delete(card);
    }

}
