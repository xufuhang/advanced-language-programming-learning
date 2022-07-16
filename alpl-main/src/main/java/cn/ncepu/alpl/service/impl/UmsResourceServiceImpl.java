package cn.ncepu.alpl.service.impl;

import cn.ncepu.alpl.dao.UmsResourceDao;
import cn.ncepu.alpl.mapper.UmsResourceMapper;
import cn.ncepu.alpl.model.UmsResource;
import cn.ncepu.alpl.model.UmsResourceExample;
import cn.ncepu.alpl.model.UmsRole;
import cn.ncepu.alpl.service.UmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xufuhang
 * @date 2022/4/15-21:56
 */
@Service
public class UmsResourceServiceImpl implements UmsResourceService {

    @Autowired
    private UmsResourceMapper resourceMapper;
    @Autowired
    private UmsResourceDao resourceDao;

    @Override
    public List<UmsResource> list() {
        List<UmsResource> resourceList = resourceMapper.selectByExample(null);
        return resourceList;
    }

    @Override
    public List<UmsResource> queryByRoleList(List<UmsRole> roleList) {
        List<UmsResource> res = new ArrayList<>();
        for (UmsRole role : roleList) {
            List<UmsResource> resourceList = resourceDao.selectByRoleId(role.getId());
            res.addAll(resourceList);
        }
        return res;
    }
}
