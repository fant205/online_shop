package com.alexey.shop.services;

import com.alexey.shop.dto.ItemDto;
import com.alexey.shop.mapper.ItemMapper;
import com.alexey.shop.model.Item;
import com.alexey.shop.repository.CartRepository;
import com.alexey.shop.validators.CartValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartValidator cartValidator;

    public List<ItemDto> findAll() {
        List<Item> all = cartRepository.findAll();
        return ItemMapper.MAPPER.fromItem(all);
    }

    public void add(ItemDto itemDto) {
        cartValidator.validate(itemDto);
        Optional<Item> optional = cartRepository.findById(itemDto.getProduct().getId());
        if (optional.isPresent()) {
            Item item = optional.get();
//            itemDto.setCount(item.getCount() + 1);
            item.setCount(item.getCount() + 1);
//            ItemMapper.MAPPER.update(item, itemDto);
            cartRepository.update(item);
        } else {
            Item item = ItemMapper.MAPPER.toItem(itemDto);
            item.setCount(1);
            cartRepository.create(item);
        }
    }

    public void modify(ItemDto itemDto) {
        cartValidator.validate(itemDto);
        Item item = cartRepository.findById(itemDto.getProduct().getId()).orElseThrow();
        ItemMapper.MAPPER.update(item, itemDto);
        cartRepository.update(item);
    }

    public void delete(Long id) {
        cartRepository.delete(id);
    }
}
