package com.ufrn.nei.almoxarifadoapi.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufrn.nei.almoxarifadoapi.dto.item.ItemCreateDTO;
import com.ufrn.nei.almoxarifadoapi.dto.item.ItemDetailsDTO;
import com.ufrn.nei.almoxarifadoapi.dto.item.ItemUpdateDTO;
import com.ufrn.nei.almoxarifadoapi.dto.mapper.ItemMapper;
import com.ufrn.nei.almoxarifadoapi.entity.ItemEntity;
import com.ufrn.nei.almoxarifadoapi.repository.ItemRepository;

import jakarta.transaction.Transactional;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public List<ItemDetailsDTO> findAllItems() {
        return ItemMapper.toListDetailsDTO(itemRepository.findAllByActiveTrue());
    }

    public ItemDetailsDTO findItem(Long id) {
        ItemEntity item = itemRepository.findById(id).orElse(null);

        return item == null || item.getActive() == false ? null : ItemMapper.toDetailsDTO(item);
    }

    @Transactional
    public ItemDetailsDTO createItem(ItemCreateDTO data) {
        ItemEntity item = ItemMapper.toItem(data);

        if (item == null) {
            throw new RuntimeException("Não foi possível converter o modelo em uma entidade.");
        }
        item.setAvailable(true);

        itemRepository.save(item);
        return ItemMapper.toDetailsDTO(item);
    }

    @Transactional
    public ItemDetailsDTO updateItem(ItemUpdateDTO data) {
        ItemEntity item = itemRepository
                .findById(data.getId())
                .orElseThrow(() -> new RuntimeException(
                        String.format("Não foi possível encontrar item com id %d", data.getId())));

        if (data.getName() != null) {
            item.setName(data.getName());
        }
        if (data.getItemTagging() != null) {
            item.setItemTagging(data.getItemTagging());
        }
        item.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

        itemRepository.save(item);
        return ItemMapper.toDetailsDTO(item);
    }

}
