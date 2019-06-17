package com.asmirnov.quilzistServer.service;

import com.asmirnov.quilzistServer.dto.ModuleCardsDTO;
import com.asmirnov.quilzistServer.model.Card;
import com.asmirnov.quilzistServer.model.Module;
import com.asmirnov.quilzistServer.repository.CardRepo;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
@Slf4j
public class CardService {
    @Autowired
    private CardRepo cardRepo;

    @Autowired
    private ModuleCardRestService mcRestService;

    @Autowired
    private ModuleInfoService moduleInfoService;

    private ModuleCardsDTO moduleCardsDTO;

    public void deleteByModule(Module module) {
        List<Card> cardList = cardRepo.findByModule(module);
        log.debug("deleteByModule({}) : {}", module.getName(), cardList.toString());
        cardRepo.deleteAll(cardList);
    }

    public List<Card> getCardsByModule(Module module) {
        List<Card> cardList = cardRepo.findByModule(module);
        log.debug("getCardsByModule({}) : {}", module.getName(), cardList.toString());
        return cardList;
    }

    public void deleteCard(Card card) {
        log.debug("deleteCard: {}", card.toString());
        cardRepo.delete(card);
    }

    public Card createCard(Module module, Card card) {
        card.setModule(module);
        Card newCard = cardRepo.save(card);
        log.debug("createCard: {}", newCard.toString());
        return newCard;
    }

    public ModuleCardsDTO createCards(Module module, List<Card> cardList) {
        ModuleCardsDTO requestDTO = new ModuleCardsDTO();

        try {
            for (Card card : cardList) {
                Card newCard = createCard(module,card);
                card.setId(newCard.getId());
            }
            Integer itemsCount = Math.toIntExact(cardRepo.findByModule(module).size());
            moduleInfoService.updateModuleInfoItemsCount(module,itemsCount);
            requestDTO.setResponseCode(0);
        }catch(Exception e){
            e.printStackTrace();
            requestDTO.setResponseCode(99, e.getMessage());
        }

        return requestDTO;
    }

    public Card updateCard(Card cardFromDB, Card card) {
        BeanUtils.copyProperties(card,cardFromDB,"id");
        log.debug("updateCard: {}", card.toString());
        return cardRepo.save(cardFromDB);
    }

    public ModuleCardsDTO updateCards(Module module, List<Card> cardsList) {

        ModuleCardsDTO requestDTO = new ModuleCardsDTO();

        Card cardFromDB;
        try {
            for (Card card : cardsList) {
                int cardId = card.getId();
                if (cardRepo.existsById(cardId)) {
                    cardFromDB = cardRepo.findById(cardId).get();
                    if (!cardFromDB.equals(card)) {
                        updateCard(cardFromDB, card);
                    }
                } else {
                    Card newCard = createCard(module, card);
                    card.setId(newCard.getId());
                }
            }
            List cardsToDelete = cardRepo.findByModule(module);
            cardsToDelete.removeAll(cardsList);
            cardRepo.deleteAll(cardsToDelete);

            Integer itemsCount = Math.toIntExact(cardRepo.findByModule(module).size());
            moduleInfoService.updateModuleInfoItemsCount(module, itemsCount);
            requestDTO.setResponseCode(0);
        }catch(Exception e){
            e.printStackTrace();
            requestDTO.setResponseCode(99, e.getMessage());
        }

        return requestDTO;
    }
}
