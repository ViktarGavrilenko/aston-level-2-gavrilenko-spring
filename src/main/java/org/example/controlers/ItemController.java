package org.example.controlers;

import org.example.controlers.dto.ItemDTO;
import org.example.controlers.mapper.ItemDtoMapperImpl;
import org.example.models.Item;
import org.example.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;
    private ItemDtoMapperImpl dtoMapper;

    @Autowired
    public ItemController(ItemService service, ItemDtoMapperImpl dtoMapper) {
        this.itemService = service;
        this.dtoMapper = dtoMapper;
    }

    @PostMapping()
    public ResponseEntity<?> save(@RequestBody ItemDTO itemDTO) {
        Item item = dtoMapper.itemDTOToItem(itemDTO);
        itemService.save(item);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
