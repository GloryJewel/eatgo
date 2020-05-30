package com.gloryjewel.eatgo.application;

import com.gloryjewel.eatgo.domain.MenuItem;
import com.gloryjewel.eatgo.domain.MenuItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class MenuItemServiceTests {

    private MenuItemService menuItemService;

    @Mock
    private MenuItemRepository menuItemRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        this.menuItemService = new MenuItemService(
                menuItemRepository
        );
    }

    @Test
    public void bulkUpdate(){

        List<MenuItem> menuItems = new ArrayList<>();

        menuItems.add(MenuItem.builder()
                .name("kimchi")
                .build());
        menuItems.add(MenuItem.builder()
                .id(1L)
                .name("gugbob")
                .build());
        menuItems.add(MenuItem.builder()
                .id(2L)
                .name("kimchi2")
                .destroy(true)
                .build());

        menuItemService.bulkUpdate(2L, menuItems);

        verify(menuItemRepository, times(2)).save(any());
        verify(menuItemRepository, times(1)).deleteById(eq(2L));
    }

    @Test
    public void getMenuItems(){

        MenuItem resource = MenuItem.builder()
                .name("kimchi")
                .build();
        given(menuItemRepository.findAllByRestaurantId(1L)).willReturn(Arrays.asList(resource));

        List<MenuItem> menuItems = menuItemService.getMenuItems(1L);

        MenuItem menuItem = menuItems.get(0);

        assertEquals(menuItem.getName(), "kimchi");
    }


}