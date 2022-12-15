package com.alexey.shop.mapper;

import com.alexey.shop.dto.ItemDto;
import com.alexey.shop.model.Item;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {ProductMapper.class})
public interface ItemMapper {
    ItemMapper MAPPER = Mappers.getMapper(ItemMapper.class);

    Item toItem(ItemDto itemDto);

    void update(@MappingTarget Item item, ItemDto itemDto);

    @InheritInverseConfiguration(name = "toItem")
    ItemDto fromItem(Item item);

    List<ItemDto> fromItem(List<Item> list);
}
