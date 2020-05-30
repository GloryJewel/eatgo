package com.gloryjewel.eatgo.application;

import com.gloryjewel.eatgo.domain.Region;
import com.gloryjewel.eatgo.domain.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionService {

    private RegionRepository regionRepository;

    @Autowired
    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public List<Region> getRegions() {
        return (List<Region>) regionRepository.findAll();
    }
}
