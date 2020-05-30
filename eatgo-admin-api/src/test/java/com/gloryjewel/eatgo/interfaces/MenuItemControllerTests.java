package com.gloryjewel.eatgo.interfaces;

import com.gloryjewel.eatgo.application.MenuItemService;
import com.gloryjewel.eatgo.domain.MenuItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MenuItemController.class)
class MenuItemControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean(MenuItemService.class)
    private MenuItemService menuItemService;

    @Test
    public void bulkUpdate() throws Exception {
        mvc.perform(patch("/restaurants/1/menuitems")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[{\"name\":\"bar\",\"address\":\"BeRung\"}]"))
                .andExpect(status().isOk());

        verify(menuItemService).bulkUpdate(eq(1L), any());
    }

    @Test
    public void list() throws Exception {

        MenuItem resource = MenuItem.builder()
                .name("kimchi")
                .restaurantId(1L)
                .build();
        given(menuItemService.getMenuItems(1L)).willReturn(Arrays.asList(resource));

        mvc.perform(get("/restaurants/1/menuitems")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("\"restaurantId\":1")));


    }
}