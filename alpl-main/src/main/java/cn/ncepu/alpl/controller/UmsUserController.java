package cn.ncepu.alpl.controller;

import cn.ncepu.alpl.api.CommonPage;
import cn.ncepu.alpl.api.CommonResult;
import cn.ncepu.alpl.api.ResultEnum;
import cn.ncepu.alpl.domain.TokenResult;
import cn.ncepu.alpl.domain.UmsUserDetail;
import cn.ncepu.alpl.domain.UmsUserLoginParam;
import cn.ncepu.alpl.model.UmsRole;
import cn.ncepu.alpl.model.UmsUser;
import cn.ncepu.alpl.service.UmsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * @author xufuhang
 * @date 2022/3/27-13:15
 */
@RestController
@RequestMapping("/ums/user")
public class UmsUserController {

    @Autowired
    UmsUserService userService;

    @PostMapping("/create/register")
    public CommonResult<UmsUser> register(@RequestBody UmsUser user) {
        user = userService.register(user);
        if (user == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(user);
    }

    @PostMapping("/create/batch")
    public CommonResult<Map<String, Integer>> batchRegister(MultipartFile file) {
        Map<String, Integer> res = userService.batchRegister(file);
        return CommonResult.success(res);
    }

    @DeleteMapping("/delete")
    public CommonResult delete(Integer id) {
        int count = userService.delete(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @PostMapping("/query/login")
    public CommonResult<TokenResult> login(@RequestBody UmsUserLoginParam loginParam) {
        TokenResult tokenResult;
        try {
            tokenResult = userService
                    .login(loginParam.getUsername(), loginParam.getPassword());
        } catch (AuthenticationException authException) {
            return CommonResult.failed(ResultEnum.AUTHENTICATE_FAILED, authException.getMessage());
        }
        return CommonResult.success(tokenResult);
    }

    @GetMapping("/query/self")
    public CommonResult<UmsUserDetail> query(Principal principal) {
        UmsUserDetail userDetail = userService.query(principal);
        if (userDetail == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(userDetail);
    }

    @GetMapping("/query/list")
    public CommonResult<CommonPage<UmsUser>> queryList(@RequestParam(required = false) String keyword,
                                                       @RequestParam(required = false) Integer roleId,
                                                       @RequestParam(defaultValue = "1") Integer pageNum,
                                                       @RequestParam(defaultValue = "5") Integer pageSize) {
        CommonPage<UmsUser> page = userService.queryList(pageNum, pageSize, keyword, roleId);
        return CommonResult.success(page);
    }

    @GetMapping("/query/role")
    public CommonResult<List<UmsRole>> queryRoleList(Integer id) {
        List<UmsRole> roleList = userService.queryRoleList(id);
        return CommonResult.success(roleList);
    }

    /**
     * 请求参数为List时，需要加上RequestParam注解，否则无法识别
     * @param userId 用户id
     * @param roleIdList 角色id列表
     */
    @PutMapping("/update/role")
    public CommonResult allocRoleList(Integer userId,
                                      @RequestParam("roleIdList") List<Integer> roleIdList) {
        int count = userService.allocRoleList(userId, roleIdList);
        if (count >= 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @PutMapping("/update/avatar")
    public CommonResult uploadAvatar(Principal principal, MultipartFile file) {
        int count = userService.uploadAvatar(principal, file);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @PutMapping("/update")
    public CommonResult update(@RequestBody UmsUser user) {
        int count = userService.update(user);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
}
