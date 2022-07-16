package cn.ncepu.alpl.service.impl;

import cn.ncepu.alpl.dao.UmsOptionDao;
import cn.ncepu.alpl.mapper.UmsOptionMapper;
import cn.ncepu.alpl.model.UmsOption;
import cn.ncepu.alpl.model.UmsRole;
import cn.ncepu.alpl.service.UmsOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xufuhang
 * @date 2022/5/5-21:31
 */
@Service
public class UmsOptionServiceImpl implements UmsOptionService {

    @Autowired
    private UmsOptionMapper optionMapper;
    @Autowired
    private UmsOptionDao optionDao;

    @Override
    public List<UmsOption> listAll() {
        List<UmsOption> optionList = optionMapper.selectByExample(null);
        return optionList;
    }

    @Override
    public List<UmsOption> queryByRoleList(List<UmsRole> roleList) {
        List<UmsOption> res = new ArrayList<>();
        for (UmsRole role : roleList) {
            List<UmsOption> optionList = optionDao.queryByRoleId(role.getId());
            res.addAll(optionList);
        }
        return res;
    }

}
