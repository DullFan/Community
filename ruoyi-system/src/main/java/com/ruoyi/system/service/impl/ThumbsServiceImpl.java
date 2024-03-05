package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.Thumbs;
import com.ruoyi.system.mapper.ThumbsMapper;
import com.ruoyi.system.service.ThumbsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThumbsServiceImpl implements ThumbsService {
    @Autowired
    private ThumbsMapper thumbsMapper;

    @Override
    public Thumbs selectThumbsById(Long id) {
        return thumbsMapper.selectThumbsById(id);
    }

    @Override
    public Thumbs selectThumbsByThumb(Thumbs thumb) {
        return thumbsMapper.selectThumbsByThumb(thumb);
    }

    @Override
    public List<Thumbs> selectThumbsList(Thumbs thumbs) {
        return thumbsMapper.selectThumbsList(thumbs);
    }

    @Override
    public Long insertThumbs(Thumbs thumbs) {
        return thumbsMapper.insertThumbs(thumbs);
    }

    @Override
    public Long updateThumbs(Thumbs thumbs) {
        return thumbsMapper.updateThumbs(thumbs);
    }

    @Override
    public Long deleteThumbsByIds(Long[] ids) {
        return thumbsMapper.deleteThumbsByIds(ids);
    }

    @Override
    public Long deleteThumbsById(Long id) {
        return thumbsMapper.deleteThumbsById(id);
    }
}
