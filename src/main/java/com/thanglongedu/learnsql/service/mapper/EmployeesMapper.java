package com.thanglongedu.learnsql.service.mapper;

import com.thanglongedu.learnsql.domain.*;
import com.thanglongedu.learnsql.service.dto.EmployeesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Employees and its DTO EmployeesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EmployeesMapper extends EntityMapper<EmployeesDTO, Employees> {


    @Mapping(target = "employeesIDS", ignore = true)
    Employees toEntity(EmployeesDTO employeesDTO);

    default Employees fromId(Long id) {
        if (id == null) {
            return null;
        }
        Employees employees = new Employees();
        employees.setId(id);
        return employees;
    }
}
