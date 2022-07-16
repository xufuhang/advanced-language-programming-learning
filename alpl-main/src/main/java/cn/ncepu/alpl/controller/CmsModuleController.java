package cn.ncepu.alpl.controller;

import cn.ncepu.alpl.api.CommonResult;
import cn.ncepu.alpl.model.CmsModule;
import cn.ncepu.alpl.service.CmsModuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
@author xufuhang
@date 2022/3/13-17:55
*/
@RestController
@RequestMapping("/cms/module")
@Slf4j
public class CmsModuleController {

    @Autowired
    CmsModuleService cmsModuleService;

    @PostMapping("/create")
    public CommonResult<Void> create(CmsModule module, Principal principal) {
        int insert = cmsModuleService.create(module, principal);
        if (insert < 1) {
            return CommonResult.failed("存在标题相同的模块");
        }
        return CommonResult.success(null);
    }

    @DeleteMapping("/delete")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public CommonResult<Void> delete(Integer id) {
        int delete = cmsModuleService.delete(id);
        if (delete < 1) {
            return CommonResult.failed();
        }
        return CommonResult.success();
    }

    @GetMapping("/query/list")
    public CommonResult<List<CmsModule>> queryList() {
        List<CmsModule> modules = cmsModuleService.selectList();
        return CommonResult.success(modules);
    }

    @PutMapping("/update")
    public CommonResult<Void> update(CmsModule module) {
        int update = cmsModuleService.updateByIdSelective(module);
        if (update < 1) {
            return CommonResult.failed();
        }
        return CommonResult.success();
    }

    /**
     * @param module 传入id和要排到的位置sort
     * @return 通用返回结果
     */
    @PutMapping("/update/position")
    public CommonResult<Void> updatePosition(CmsModule module) {
        int update = cmsModuleService.updatePosition(module);
        if (update < 1) {
            return CommonResult.failed();
        }
        return CommonResult.success();
    }


}
