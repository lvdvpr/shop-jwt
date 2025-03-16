package com.apple.shop.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public void saveItem(String title, String price, String image_url, String username) {
        // Object 만들기
        Item item = new Item();
        // title 값 넣기
        item.setTitle(title);
        // price 값 넣기
        String numberWithCommas = price;
        String cleanedNumber = numberWithCommas.replace(",", "");
        item.setPrice(Integer.valueOf(cleanedNumber));
        item.setImage_url(image_url);
        item.setUsername(username);
        itemRepository.save(item);
    }

    public List<Item> findAllItem() {
        List<Item> itemList = itemRepository.findAll();
        return itemList;
    }

    public Optional<Item> findItemById (Long id) {
        Optional<Item> item = itemRepository.findById(id);
        return item;
    }

    public void editItem(String title, String price, Long id) {
        Item item = new Item();
        item.setId(id);
        item.setTitle(title);
        String numberWithCommas = price;
        String cleanedNumber = numberWithCommas.replace(",", "");
        item.setPrice(Integer.valueOf(cleanedNumber));
        itemRepository.save(item);
    }

    public void deleteItemById(Long id) {
        itemRepository.deleteById(id);
    }
}
