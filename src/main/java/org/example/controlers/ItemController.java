package org.example.controlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.controlers.dto.ItemDTO;
import org.example.controlers.mapper.ItemDtoMapperImpl;
import org.example.models.Item;
import org.example.services.impl.ItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/item")
public class ItemController {
    private final ItemServiceImpl itemService;
    private final ItemDtoMapperImpl dtoMapper;
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public ItemController(ItemServiceImpl itemService, ItemDtoMapperImpl dtoMapper) {
        this.itemService = itemService;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ItemDTO> get(@PathVariable("id") int id) {
        Item item = itemService.get(id);
        ItemDTO itemDTO = null;
        if (item != null) {
            itemDTO = dtoMapper.itemToItemDTO(item);
        }
        return new ResponseEntity<>(itemDTO, HttpStatus.OK);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<ItemDTO>> getAll() {
        List<Item> items = itemService.getAll();
        List<ItemDTO> itemDTOS = new ArrayList<>();
        for (Item item : items) {
            itemDTOS.add(dtoMapper.itemToItemDTO(item));
        }
        return new ResponseEntity<>(itemDTOS, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> save(@RequestBody String json) throws JsonProcessingException {
        ItemDTO itemDTO = mapper.readValue(json, ItemDTO.class);
        Item item = dtoMapper.itemDTOToItem(itemDTO);
        itemService.save(item);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    protected ResponseEntity<?> doPut(@RequestBody String json) throws JsonProcessingException {
        ItemDTO itemDTO = mapper.readValue(json, ItemDTO.class);
        Item item = dtoMapper.itemDTOToItem(itemDTO);
        itemService.update(item);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    protected ResponseEntity<?> doDelete(@RequestParam("id") int id) {
        itemService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}