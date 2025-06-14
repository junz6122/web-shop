package com.jun.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jun.backend.entity.MyFile;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper extends BaseMapper<MyFile> {
}
