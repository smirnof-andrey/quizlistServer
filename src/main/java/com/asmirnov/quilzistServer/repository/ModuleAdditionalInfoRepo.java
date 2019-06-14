package com.asmirnov.quilzistServer.repository;

import com.asmirnov.quilzistServer.model.Module;
import com.asmirnov.quilzistServer.model.ModuleAdditionalInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleAdditionalInfoRepo extends CrudRepository<ModuleAdditionalInfo, Integer> {

    List<ModuleAdditionalInfo> findAll();
    ModuleAdditionalInfo findByModule(Module module);

    @Query("select m from ModuleAdditionalInfo m where m.module in (:moduleList)")
    List<ModuleAdditionalInfo> findByModuleList(@Param("moduleList")List<Module> moduleList);

}
