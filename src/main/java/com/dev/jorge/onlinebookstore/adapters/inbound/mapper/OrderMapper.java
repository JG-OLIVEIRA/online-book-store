package com.dev.jorge.onlinebookstore.adapters.inbound.mapper;

import com.dev.jorge.onlinebookstore.domain.order.Order;
import com.dev.jorge.onlinebookstore.adapters.inbound.dto.order.CreateOrderRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "id", ignore = true)
    Order toDomain(CreateOrderRequestDTO request);
}
