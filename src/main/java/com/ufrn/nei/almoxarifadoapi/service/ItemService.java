package com.ufrn.nei.almoxarifadoapi.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import com.ufrn.nei.almoxarifadoapi.exception.CreateEntityException;
import com.ufrn.nei.almoxarifadoapi.exception.EntityNotFoundException;
import com.ufrn.nei.almoxarifadoapi.exception.ItemNotActiveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufrn.nei.almoxarifadoapi.dto.item.ItemCreateDTO;
import com.ufrn.nei.almoxarifadoapi.dto.item.ItemResponseDTO;
import com.ufrn.nei.almoxarifadoapi.dto.item.ItemUpdateDTO;
import com.ufrn.nei.almoxarifadoapi.dto.mapper.ItemMapper;
import com.ufrn.nei.almoxarifadoapi.entity.ItemEntity;
import com.ufrn.nei.almoxarifadoapi.repository.ItemRepository;

import jakarta.transaction.Transactional;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public List<ItemResponseDTO> findAllItems() {
        return ItemMapper.toListResponseDTO(itemRepository.findAllByActiveTrue());
    }

    public ItemResponseDTO findItem(Long id) {
        return ItemMapper.toResponseDTO(itemRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Item não encontrado com id=%s", id))
        ));
    }

    @Transactional
    public ItemResponseDTO createItem(ItemCreateDTO data) {
        ItemEntity item = ItemMapper.toItem(data);

        if (item == null) {
            throw new CreateEntityException("Erro na criação da entidade item");
        }

        item.setAvailable(true);
        itemRepository.save(item);

        return ItemMapper.toResponseDTO(item);
    }

    @Transactional
    public ItemResponseDTO updateItem(Long id, ItemUpdateDTO data) {
        ItemEntity item = ItemMapper.toItem(findItem(id));

        if (data.getName() != null && !data.getName().isBlank()) {
            item.setName(data.getName());
        }
        if (data.getItemTagging() != null) {
            item.setItemTagging(data.getItemTagging());
        }
        item.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

        itemRepository.save(item);
        return ItemMapper.toResponseDTO(item);
    }

    @Transactional
    public boolean deleteItem(Long id) {
        ItemEntity item = ItemMapper.toItem(findItem(id));

        if (item.getActive().equals(false)) {
            throw new ItemNotActiveException();
        }

        item.setActive(false);
        itemRepository.save(item);

        return true;
    }
}
