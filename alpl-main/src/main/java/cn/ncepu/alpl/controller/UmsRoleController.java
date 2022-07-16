package cn.ncepu.alpl.controller;

import cn.ncepu.alpl.api.CommonPage;
import cn.ncepu.alpl.api.CommonResult;
import cn.ncepu.alpl.model.UmsResource;
import cn.ncepu.alpl.model.UmsRole;
import cn.ncepu.alpl.service.UmsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xufuhang
 * @date 2022/3/27-13:15
 */
@RestController
@RequestMapping("/ums/role")
public class UmsRoleController {
    @Autowired
    private UmsRoleService roleService;

    @PostMapping("/create")
    public CommonResult create(@RequestBody UmsRole role) {
        int count = roleService.create(role);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @PostMapping("/create/allocResource")
    public CommonResult allocResource(@RequestParam Integer roleId,
                                      @RequestParam List<Integer> resourceIds) {
        int count = roleService.allocResource(roleId, resourceIds);
        return CommonResult.success(count);
    }

//    @DeleteMapping("/delete")
//    public CommonResult delete(@RequestParam("roleIdList") List<Integer> roleIdList) {
//        int count = roleService.delete(roleIdList);
//        if (count > 0) {
//            return CommonResult.success(count);
//        }
//        return CommonResult.failed();
//    }

    @GetMapping( "/query/listAll")
    public CommonResult<List<UmsRole>> listAll() {
        List<UmsRole> roleList = roleService.list();
        return CommonResult.success(roleList);
    }

    @GetMapping("/query/list")
    public CommonResult<CommonPage<UmsRole>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                                  @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        CommonPage<UmsRole> page = roleService.list(keyword, pageSize, pageNum);
        return CommonResult.success(page);
    }

    @PutMapping("/updateStatus")
    public CommonResult updateStatus(Integer id, Short status) {
        UmsRole umsRole = new UmsRole();
        umsRole.setStatus(status);
        int count = roleService.update(umsRole);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

//    @RequestMapping(value = "/listMenu/{roleId}", method = RequestMethod.GET)
//    @ResponseBody
//    public CommonResult<List<UmsMenu>> listMenu(@PathVariable Long roleId) {
//        List<UmsMenu> roleList = roleService.listMenu(roleId);
//        return CommonResult.success(roleList);
//    }

    @GetMapping("/query/listResource")
    public CommonResult<List<UmsResource>> listResource(Integer roleId) {
        List<UmsResource> resourceList = roleService.listResource(roleId);
        return CommonResult.success(resourceList);
    }

    @PutMapping("/update")
    public CommonResult update(@RequestBody UmsRole role) {
        int count = roleService.update(role);
        if (count > 0) {
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

//    @PostMapping("/allocMenu")
//    public CommonResult allocMenu(@RequestParam Integer roleId, @RequestParam List<Integer> menuIds) {
//        int count = roleService.allocMenu(roleId, menuIds);
//        return CommonResult.success(count);
//    }

}
