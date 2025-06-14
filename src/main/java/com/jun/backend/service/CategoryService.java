package com.jun.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jun.backend.entity.Category;
import com.jun.backend.entity.IconCategory;
import com.jun.backend.utils.BaseApi;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jun.backend.mapper.CategoryMapper;
import com.jun.backend.mapper.IconCategoryMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class CategoryService extends ServiceImpl<CategoryMapper, Category> {

    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private IconCategoryMapper iconCategoryMapper;

    /**
     *  新增下级分类 + 上下级分类关联
     *
     * @param category 下级分类
     */
    public void add(Category category) {
        save(category);
        IconCategory iconCategory = new IconCategory();
        iconCategory.setCategoryId(category.getId());
        iconCategory.setIconId(category.getIconId());
        iconCategoryMapper.insert(iconCategory);
    }

    /**
     * 删除分类
     *
     * @param id id
     * @return 结果
     */
    public Map<String, Object> delete(Long id) {
        // 删除关联
        iconCategoryMapper.delete(
                new QueryWrapper<IconCategory>().eq("category_id", id)
        );
        // 删除下级分类
        removeById(id);
        return BaseApi.success();
    }
}
