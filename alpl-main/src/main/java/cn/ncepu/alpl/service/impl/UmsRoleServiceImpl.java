package cn.ncepu.alpl.service.impl;

import cn.ncepu.alpl.api.CommonPage;
import cn.ncepu.alpl.dao.UmsRoleDao;
import cn.ncepu.alpl.mapper.*;
import cn.ncepu.alpl.model.*;
import cn.ncepu.alpl.repostory.UmsRoleCacheService;
import cn.ncepu.alpl.service.UmsRoleService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author xufuhang
 * @date 2022/3/27-13:15
 */
@Service
public class UmsRoleServiceImpl implements UmsRoleService {
    @Autowired
    private UmsRoleMapper roleMapper;
    @Autowired
    private UmsRoleResourceRelationMapper roleResourceRelationMapper;
    @Autowired
    private UmsRoleDao roleDao;
    @Autowired
    private UmsResourceMapper resourceMapper;
    @Autowired
    private UmsRoleOptionRelationMapper roleOptionRelationMapper;
    @Autowired
    private UmsOptionMapper optionMapper;
    @Autowired
    private UmsRoleCacheService roleCacheService;

    @Override
    public int create(UmsRole role) {
        role.setCreateTime(new Date());
        role.setStatus((short) 1);
        return roleMapper.insert(role);
    }

    @Override
    public int update(UmsRole role) {
        return roleMapper.updateByPrimaryKeySelective(role);
    }

    /**
     * 暂时用不到此方法
     */
    @Override
    public int delete(List<Integer> roleIdList) {
        UmsRoleExample example = new UmsRoleExample();
        example.createCriteria().andIdIn(roleIdList);
        int count = roleMapper.deleteByExample(example);

//        userCacheService.delResourceListByRoleIds(roleIdList);
        return count;
    }

    /**
     * 不缓存该方法，因为数据少，访问不频繁，而访问时经常会修改
     * @return 角色列表
     */
    @Override
    public List<UmsRole> list() {
        return roleMapper.selectByExample(new UmsRoleExample());
    }

    /**
     * 同上
     * @return 角色列表分页
     */
    @Override
    public CommonPage<UmsRole> list(String keyword, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        UmsRoleExample example = new UmsRoleExample();
        if (!StringUtils.isEmpty(keyword)) {
            example.createCriteria().andNameLike("%" + keyword + "%");
        }
        List<UmsRole> roleList = roleMapper.selectByExample(example);
        CommonPage<UmsRole> page = CommonPage.genCommonPage(roleList);
        return page;
    }

    @Override
    public List<UmsResource> queryResourceByRoleIdList(List<UmsRole> roleList) {
        Set<UmsResource> resourceSet = new HashSet<>();
        for (UmsRole role : roleList) {
            List<UmsResource> resourceList = listResource(role.getId());
            resourceSet.addAll(resourceList);
        }
        return new ArrayList<>(resourceSet);
    }

    @Override
    public List<UmsOption> queryOptionListByRoleIdList(List<UmsRole> roleList) {
        Set<UmsOption> optionSet = new HashSet<>();
        for (UmsRole role : roleList) {
            List<UmsOption> optionList = queryOptionListByRoleId(role.getId());
            optionSet.addAll(optionList);
        }
        return new ArrayList<>(optionSet);
    }

    @Override
    public List<UmsResource> listResource(Integer roleId) {
        List<UmsResource> resourceList = roleCacheService.getResourceList(roleId);
        if (resourceList != null) {
            return resourceList;
        }

        resourceList = roleDao.selectResourceByRoleId(roleId);
        roleCacheService.setResourceList(roleId, resourceList);
        return resourceList;
    }

    @Override
    public int allocResource(Integer roleId, List<Integer> resourceIdList) {
        //先删除原有资源关系
        UmsRoleResourceRelationExample example = new UmsRoleResourceRelationExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        roleResourceRelationMapper.deleteByExample(example);

        //删除原有菜单操作关系
        UmsRoleOptionRelationExample optionRelationExample = new UmsRoleOptionRelationExample();
        optionRelationExample.createCriteria().andRoleIdEqualTo(roleId);
        roleOptionRelationMapper.deleteByExample(optionRelationExample);
        //批量插入新关系
        for (Integer resourceId : resourceIdList) {
            UmsRoleResourceRelation relation = new UmsRoleResourceRelation();
            relation.setRoleId(roleId);
            relation.setResourceId(resourceId);
            roleResourceRelationMapper.insert(relation);
        }

        UmsResourceExample resourceExample = new UmsResourceExample();
        resourceExample.createCriteria().andIdIn(resourceIdList);
        List<UmsResource> resourceList = resourceMapper.selectByExample(resourceExample);
        for (UmsResource resource : resourceList) {
            // 分配资源对应的前端菜单，option即前端的一个按钮或者链接等
            Integer optionId = resource.getOptionId();
            if (optionId == null) {
                // 如果资源没有对应前端菜单，则跳过
                continue;
            }
            UmsOption option = optionMapper.selectByPrimaryKey(optionId);
            insertRoleOptionRelation(roleId, option.getId());
            while (option.getParentId() != 0) {
                // 若父节点不为空，则添加父节点，并继续向上查找
                UmsOption parentOption = optionMapper.selectByPrimaryKey(option.getParentId());
                insertRoleOptionRelation(roleId, parentOption.getId());
                option = parentOption;
            }
        }

        roleCacheService.delResourceList(roleId);
        roleCacheService.delOptionList(roleId);
        return resourceIdList.size();
    }

    private void insertRoleOptionRelation(Integer roleId, Integer optionId) {
        UmsRoleOptionRelation roleOptionRelation = new UmsRoleOptionRelation();
        roleOptionRelation.setRoleId(roleId);
        roleOptionRelation.setOptionId(optionId);
        roleOptionRelationMapper.insert(roleOptionRelation);
    }

    private List<UmsOption> queryOptionListByRoleId(Integer roleId) {
        List<UmsOption> optionList = roleCacheService.getOptionListByRoleId(roleId);
        if (optionList != null) {
            return optionList;
        }

        optionList = roleDao.selectOptionByRoleId(roleId);
        roleCacheService.setOptionList(roleId, optionList);
        return optionList;
    }
}
