package com.jun.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jun.backend.entity.Icon;
import com.jun.backend.entity.IconCategory;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jun.backend.mapper.IconCategoryMapper;
import com.jun.backend.mapper.IconMapper;
import com.jun.backend.utils.BaseApi;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class IconService extends ServiceImpl<IconMapper, Icon> {

    @Resource
    private IconMapper iconMapper;

    @Resource
    private IconCategoryMapper iconCategoryMapper;

    public List<Icon> getIconCategoryMapList() {
        return iconMapper.getIconCategoryMapList();
    }

    /**
     * 删除上级分类
     *
     * @param id id
     */
    public Map<String, Object> deleteById(Long id) {
        // 检查是否包含下级分类
        Long count = iconCategoryMapper.selectCount(
                new QueryWrapper<IconCategory>().eq("icon_id", id)
        );
        if (count > 0) {
            return BaseApi.error("该上级分类存在下级分类，请删除所有下级分类再尝试删除");
        }
        super.removeById(id);
        return BaseApi.success();
    }
}
