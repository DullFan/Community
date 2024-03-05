package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.Image;
import com.ruoyi.system.mapper.ImageMapper;
import com.ruoyi.system.service.ImageService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    ImageMapper imageMapper;

    @Override
    public List<Image> selectImageById(Long quoteId, Long type) {
        return imageMapper.selectImageById(quoteId, type);
    }

    @Override
    public Long insertImage(Image image) {
        return imageMapper.insertImage(image);
    }

    @Override
    public AjaxResult insertImages(List<String> urls, Image image) {
        if (!urls.isEmpty()) {
            // 插入文章图片
            for (String s : urls) {
                System.out.println(s);
                image.setImageUrl(s);
                insertImage(image);
            }
            return AjaxResult.success();
        } else return AjaxResult.error();
    }

    @Override
    public Long deleteImageById(Long id) {
        return imageMapper.deleteImageById(id);
    }

    @Override
    public Long deleteImageByArticlesId(Long quoteId, Long type) {
        return imageMapper.deleteImageByArticlesId(quoteId, type);
    }

    @Override
    public Long deleteImageByArticlesIds(Long[] quoteIds,Long type) {
        return imageMapper.deleteImageByArticlesIds(quoteIds, type);
    }
}
