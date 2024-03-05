package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.Browse;
import com.ruoyi.system.mapper.BrowseMapper;
import com.ruoyi.system.service.BrowseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrowseServiceImpl implements BrowseService {
    @Autowired
    BrowseMapper browseMapper;

    @Override
    public Browse selectBrowseById(Long id) {
        return browseMapper.selectBrowseById(id);
    }

    @Override
    public Browse selectBrowseByBrowse(Browse browse) {
        return browseMapper.selectBrowseByBrowse(browse);
    }

    @Override
    public List<Browse> selectBrowseList(Browse browse) {
        return browseMapper.selectBrowseList(browse);
    }

    @Override
    public Long insertBrowse(Browse browse) {
        return browseMapper.insertBrowse(browse);
    }

    @Override
    public Long updateBrowse(Browse browse) {
        return browseMapper.updateBrowse(browse);
    }

    @Override
    public Long deleteBrowseByIds(Long[] ids) {
        return browseMapper.deleteBrowseByIds(ids);
    }

    @Override
    public Long deleteBrowseById(Long id) {
        return browseMapper.deleteBrowseById(id);
    }
}
