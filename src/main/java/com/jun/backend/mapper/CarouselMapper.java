package com.jun.backend.mapper;

import com.jun.backend.entity.Carousel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface CarouselMapper extends BaseMapper<Carousel> {

    List<Carousel> getAllCarousel();
}
