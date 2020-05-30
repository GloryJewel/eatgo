package com.gloryjewel.eatgo.application;

import com.gloryjewel.eatgo.domain.Region;
import com.gloryjewel.eatgo.domain.RegionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class RegionServiceTests {

    private RegionService resionService;

    @Mock
    private RegionRepository regionRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        resionService = new RegionService(regionRepository);
    }

    @Test
    public void list(){

        Region region = Region.builder()
                .name("서울")
                .build();
        given(regionRepository.findAll()).willReturn(Arrays.asList(region));

        List<Region> regions = resionService.getRegions();

        assertEquals(regions.get(0).getName(), "서울");
   }

   @Test
   public void addRegion(){

        given(regionRepository.save(any())).will(invocation -> {

            Region region = invocation.getArgument(0);

            return Region.builder()
                    .name(region.getName())
                    .id(1L)
                    .build();
        });

        Region region = resionService.addRegion("서울");

        assertEquals(region.getId(), 1);
    }
}