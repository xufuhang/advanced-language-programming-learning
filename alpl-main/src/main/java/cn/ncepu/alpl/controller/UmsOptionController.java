package cn.ncepu.alpl.controller;

import cn.ncepu.alpl.api.CommonResult;
import cn.ncepu.alpl.model.UmsOption;
import cn.ncepu.alpl.service.UmsOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xufuhang
 * @date 2022/5/5-21:33
 */
@RestController
@RequestMapping("/ums/option")
public class UmsOptionController {

    @Autowired
    UmsOptionService optionService;

    public CommonResult<List<UmsOption>> listAll() {
        List<UmsOption> optionList = optionService.listAll();
        return CommonResult.success(optionList);
    }

}
