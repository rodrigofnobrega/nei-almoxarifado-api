package com.ufrn.nei.almoxarifadoapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
        items.add(new ItemEntity(1L, "Cadeira", 202012L, true, Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()), true, new ArrayList<RecordEntity>()));
        items.add(new ItemEntity(2L, "Mesa", 202013L, true, Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()), true, new ArrayList<RecordEntity>()));
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve listar DTOs corretamente")
    void getAllItemsTest() {
        Mockito.when(itemRepository.findAllByActiveTrue()).thenReturn(items);

        List<ItemResponseDTO> response = itemService.findAllItems();

        verify(itemRepository, times(1)).findAllByActiveTrue();
        assertNotNull(response);
        assertEquals(ItemMapper.toListResponseDTO(items), response);
    }
}
