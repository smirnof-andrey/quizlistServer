package com.asmirnov.quilzistServer.repository;

import com.asmirnov.quilzistServer.model.Module;
import com.asmirnov.quilzistServer.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ModuleRepo extends CrudRepository<Module, Integer> {
    List<Module> findByInfoLike(String info);
    List<Module> findByAuthor(User author);
    List<Module> findByAuthorAndName(User author, String name);

}
