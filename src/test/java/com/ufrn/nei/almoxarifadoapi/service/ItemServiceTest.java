package com.ufrn.nei.almoxarifadoapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ufrn.nei.almoxarifadoapi.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ufrn.nei.almoxarifadoapi.dto.item.ItemCreateDTO;
import com.ufrn.nei.almoxarifadoapi.dto.item.ItemResponseDTO;
import com.ufrn.nei.almoxarifadoapi.dto.mapper.ItemMapper;
import com.ufrn.nei.almoxarifadoapi.entity.ItemEntity;
import com.ufrn.nei.almoxarifadoapi.entity.RecordEntity;
import com.ufrn.nei.almoxarifadoapi.repository.ItemRepository;

public class ItemServiceTest {
    @Mock
    private ItemRepository itemRepository;
    @InjectMocks
    private ItemService itemService;

    private List<ItemEntity> items = new ArrayList<ItemEntity>();

    @BeforeEach
    void setup() {
        items.add(new ItemEntity(null, "Cadeira", 202012L, 100, 1, Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()), true, new ArrayList<RecordEntity>()));
        items.add(new ItemEntity(null, "Mesa", 202013L, 45, 22, Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()), true, new ArrayList<RecordEntity>()));
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve listar DTOs corretamente")
    void getAllItemsTest() {
        when(itemRepository.findAllByActiveTrue()).thenReturn(items);

        List<ItemResponseDTO> response = itemService.findAllItems();

        verify(itemRepository, times(1)).findAllByActiveTrue();
        assertNotNull(response);
        assertEquals(ItemMapper.toListResponseDTO(items), response);
    }

    @Test
    @DisplayName("Deve cadastrar Item corretamente")
    void createItemTest() {
        ItemEntity item = items.get(0);
        ItemCreateDTO itemDTO = new ItemCreateDTO(item.getName(), item.getItemTagging(), item.getQuantityAvailable());

        when(itemRepository.save(item)).thenReturn(item);
        ItemResponseDTO response = itemService.createItem(itemDTO);

        verify(itemRepository, times(1)).save(item);
        assertNotNull(response);
        assertEquals(item.getItemTagging(), response.getItemTagging());
    }

    @Test
    @DisplayName("Não deve deletar item que não existe")
    void deleteItemFailureTest() {
        EntityNotFoundException validate = assertThrows(
                EntityNotFoundException.class,
                () -> itemService.deleteItem(3L, 0));

        assertEquals("Item não encontrado com id=3", validate.getMessage());
    }

    @Test
    @DisplayName("Deve deletar item corretamente")
    void deleteItemTest() {
        ItemResponseDTO itemResponseDTO = new ItemResponseDTO(1L, "Cadeira", 202012L, 10, 0,
                Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()), true);

        when(itemService.findById(1L)).thenReturn(itemResponseDTO);

        itemService.deleteItem(1L, 10);

        verify(itemRepository, times(1)).findById(1L);
        verify(itemRepository, times(1)).deleteById(1L);
    }
}
