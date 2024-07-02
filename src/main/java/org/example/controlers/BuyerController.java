package org.example.controlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.controlers.dto.BuyerDTO;
import org.example.controlers.mapper.BuyerDtoMapper;
import org.example.models.Buyer;
import org.example.services.impl.BuyerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/buyer")

public class BuyerController {
    private final BuyerServiceImpl buyerService;
    private final BuyerDtoMapper dtoMapper;
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public BuyerController(BuyerServiceImpl buyerService, BuyerDtoMapper dtoMapper) {
        this.buyerService = buyerService;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<BuyerDTO> get(@PathVariable("id") int id) {
        Buyer buyer = buyerService.get(id);
        BuyerDTO buyerDTO = null;
        if (buyer != null) {
            buyerDTO = dtoMapper.buyerToBuyerDTO(buyer);
        }
        return new ResponseEntity<>(buyerDTO, HttpStatus.OK);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<BuyerDTO>> getAll() {
        List<Buyer> buyers = buyerService.getAll();
        List<BuyerDTO> buyerDTOS = new ArrayList<>();
        for (Buyer buyer : buyers) {
            buyerDTOS.add(dtoMapper.buyerToBuyerDTO(buyer));
        }
        return new ResponseEntity<>(buyerDTOS, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> save(@RequestBody String json) throws JsonProcessingException {
        BuyerDTO buyerDTO = mapper.readValue(json, BuyerDTO.class);
        Buyer buyer = dtoMapper.buyerDTOToBuyer(buyerDTO);
        buyerService.save(buyer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<?> doPut(@RequestBody String json) throws JsonProcessingException {
        BuyerDTO buyerDTO = mapper.readValue(json, BuyerDTO.class);
        Buyer buyer = dtoMapper.buyerDTOToBuyer(buyerDTO);
        buyerService.update(buyer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> doDelete(@RequestParam("id") int id) {
        buyerService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}