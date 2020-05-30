package com.gloryjewel.eatgo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue
    private Long id;

    private Long restaurantId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long menuId;

    @NotNull
    private Long score;

    private String name;

    @Size(max = 100)
    private String description;
}
