package com.gloryjewel.eatgo.interfaces;

import com.gloryjewel.eatgo.application.MenuItemService;
import com.gloryjewel.eatgo.domain.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    @PatchMapping("/restaurants/{id}/menuitems")
    public String bulkUpdate(@PathVariable("id") Long id,
                             @RequestBody List<MenuItem> menuItems){

        menuItemService.bulkUpdate(id, menuItems);

        return "{}";
    }

    @GetMapping("/restaurants/{id}/menuitems")
    public List<MenuItem> list(@PathVariable("id") Long id){

        return menuItemService.getMenuItems(id);
    }

}