package com.ufrn.nei.almoxarifadoapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufrn.nei.almoxarifadoapi.dto.item.ItemCreateDTO;
import com.ufrn.nei.almoxarifadoapi.dto.item.ItemDetailsDTO;
import com.ufrn.nei.almoxarifadoapi.dto.item.ItemUpdateDTO;
import com.ufrn.nei.almoxarifadoapi.service.ItemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/itens")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping
    public ResponseEntity<List<ItemDetailsDTO>> getAllItems() {
        List<ItemDetailsDTO> items = itemService.findAllItems();

        return ResponseEntity.status(HttpStatus.OK).body(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDetailsDTO> getItem(@PathVariable Long id) {
        ItemDetailsDTO item = itemService.findItem(id);

        return item == null
                ? ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
                : ResponseEntity.status(HttpStatus.OK).body(item);
    }

    @PostMapping
    public ResponseEntity<ItemDetailsDTO> createItem(@RequestBody @Valid ItemCreateDTO itemDTO) {
        ItemDetailsDTO item = itemService.createItem(itemDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    @PutMapping
    public ResponseEntity<ItemDetailsDTO> updateItem(@RequestBody @Valid ItemUpdateDTO itemDTO) {
        ItemDetailsDTO item = itemService.updateItem(itemDTO);

        return ResponseEntity.status(HttpStatus.OK).body(item);
    }

}
