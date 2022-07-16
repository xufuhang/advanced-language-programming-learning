package cn.ncepu.alpl.controller;

import cn.ncepu.alpl.api.CommonResult;
import cn.ncepu.alpl.model.UmsResource;
import cn.ncepu.alpl.service.UmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xufuhang
 * @date 2022/4/15-21:50
 */
@RestController
@RequestMapping("/ums/resource")
public class UmsResourceController {

    @Autowired
    UmsResourceService resourceService;

    @GetMapping("/query/list")
    public CommonResult<List<UmsResource>> fetchList() {
        List<UmsResource> resourceList = resourceService.list();
        return CommonResult.success(resourceList);
    }

}
