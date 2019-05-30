package com.asmirnov.quilzistServer.repository;

import com.asmirnov.quilzistServer.model.Card;
import com.asmirnov.quilzistServer.model.Module;
import com.asmirnov.quilzistServer.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CardRepo extends CrudRepository<Card, Integer> {
    List<Card> findByModule(Module module);
    List<Card> findAll();

}
