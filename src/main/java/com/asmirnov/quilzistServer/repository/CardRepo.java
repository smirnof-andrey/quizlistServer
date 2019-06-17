package com.asmirnov.quilzistServer.repository;

import com.asmirnov.quilzistServer.model.Card;
import com.asmirnov.quilzistServer.model.Module;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepo extends CrudRepository<Card, Integer> {
    List<Card> findByModule(Module module);
    List<Card> findAll();

}
