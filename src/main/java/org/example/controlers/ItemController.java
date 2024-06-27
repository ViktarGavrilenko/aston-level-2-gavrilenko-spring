package org.example.controlers;

import org.example.controlers.dto.ItemDTO;
import org.example.controlers.mapper.ItemDtoMapperImpl;
import org.example.models.Item;
import org.example.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;
    private ItemDtoMapperImpl dtoMapper;

    @Autowired
    public ItemController(ItemService service, ItemDtoMapperImpl dtoMapper) {
        this.itemService = service;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping()
    public ResponseEntity<ItemDTO> get(@RequestParam("id") int id) {
        Item item = itemService.get(id);
        ItemDTO itemDTO = null;
        if (item != null) {
            itemDTO = dtoMapper.itemToItemDTO(item);
        }
        return new ResponseEntity<>(itemDTO, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> save(@RequestBody ItemDTO itemDTO) {
        Item item = dtoMapper.itemDTOToItem(itemDTO);
        itemService.save(item);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

/*    @PutMapping
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String json = getTextFromInputStream(req.getInputStream());
        ItemDTO dto = MAPPER.readValue(json, ItemDTO.class);
        Item item = dtoMapper.itemDTOToItem(dto);
        itemService.update(item);
    }

    @DeleteMapping
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        itemService.delete(id);
    }*/
}
