package com.thanglongedu.learnsql.service.mapper;

import com.thanglongedu.learnsql.domain.*;
import com.thanglongedu.learnsql.service.dto.OrdersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Orders and its DTO OrdersDTO.
 */
@Mapper(componentModel = "spring", uses = {CustomerMapper.class, EmployeesMapper.class, ShipperMapper.class})
public interface OrdersMapper extends EntityMapper<OrdersDTO, Orders> {

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "employees.id", target = "employeesId")
    @Mapping(source = "shipper.id", target = "shipperId")
    OrdersDTO toDto(Orders orders);

    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "employeesId", target = "employees")
    @Mapping(source = "shipperId", target = "shipper")
    Orders toEntity(OrdersDTO ordersDTO);

    default Orders fromId(Long id) {
        if (id == null) {
            return null;
        }
        Orders orders = new Orders();
        orders.setId(id);
        return orders;
    }
}
