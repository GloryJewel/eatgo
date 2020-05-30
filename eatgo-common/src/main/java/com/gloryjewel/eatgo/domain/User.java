package com.gloryjewel.eatgo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @GeneratedValue
    @Id
    Long id;

    @NotEmpty
    private String email;

    @NotEmpty
    private String name;

    @NotNull
    private Long level;

    private String password;

    private Long restaurantId;

    public boolean isAdmin() {
        return level > 100;
    }

    public boolean isActive() {
        return level > 0;
    }

    public void deactivate() {
        level = 0L;
    }

    public boolean isRestaurantOwner() {

        return restaurantId != null;
    }
}
