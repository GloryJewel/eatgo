package com.gloryjewel.eatgo.domain;

import org.springframework.data.repository.CrudRepository;

public interface RegionRepository extends CrudRepository<Region, Long> {
    Region save(Region region);
}
