package com.gloryjewel.eatgo.application;

import com.gloryjewel.eatgo.domain.MenuItem;
import com.gloryjewel.eatgo.domain.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {

    private MenuItemRepository menuItemRepository;

    @Autowired
    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public void bulkUpdate(Long id, List<MenuItem> menuItems) {

        for(MenuItem item : menuItems){
            // TODO: 배치 사이즈만큼 그루핑으로 나눠서 작업한다. (db 죽는다)
            update(id, item);
        }
    }

    private void update(Long id, MenuItem item) {
        if(item.isDestroy()){
            try{
                menuItemRepository.deleteById(item.getId());
            }catch(EmptyResultDataAccessException e){}

        }else{
            item.setRestaurantId(id);
            menuItemRepository.save(item);
        }
    }

    public List<MenuItem> getMenuItems(long restaurantId) {

        return menuItemRepository.findAllByRestaurantId(restaurantId);
    }
}
