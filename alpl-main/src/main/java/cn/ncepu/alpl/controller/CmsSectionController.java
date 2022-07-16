package cn.ncepu.alpl.controller;

import cn.ncepu.alpl.api.CommonResult;
import cn.ncepu.alpl.api.ResultEnum;
import cn.ncepu.alpl.domain.CmsSectionDetail;
import cn.ncepu.alpl.model.CmsSection;
import cn.ncepu.alpl.service.CmsSectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
@author xufuhang
@date 2022/2/2-9:05
*/
@RestController
@RequestMapping("/cms/section")
@Slf4j
public class CmsSectionController {

    @Autowired
    CmsSectionService sectionService;

    @PostMapping("/create")
    public CommonResult<Void> create(CmsSectionDetail sectionResult, Principal principal) {
        int count = sectionService.create(sectionResult, principal);
        if (count < 1) {
            return CommonResult.failed();
        }
        return CommonResult.success();
    }

    @DeleteMapping("/delete")
    public CommonResult<Void> delete(Integer id) {
        int delete = sectionService.delete(id);
        if (delete < 1) {
            return CommonResult.failed();
        }
        return CommonResult.success();
    }

    @GetMapping("/query/list")
    public CommonResult<List<CmsSection>> queryList(CmsSection section) {
        List<CmsSection> sections = sectionService.queryByCondition(section);
        return CommonResult.success(sections);
    }

    @GetMapping("/query")
    public CommonResult<CmsSectionDetail> query(Integer id) {
        CmsSectionDetail sectionDetail = sectionService.queryDetailById(id);
        return CommonResult.success(sectionDetail);
    }

    @PutMapping("update/newComment")
    public CommonResult<Void> queryAndClearNewCommentCount(Integer id) {
        int update = sectionService.clearNewCommentCount(id);
        if (update < 1) {
            return CommonResult.failed();
        }
        return CommonResult.success();
    }

    @PutMapping("/update/position")
    public CommonResult<Void> updatePosition(CmsSection section) {
        int update = sectionService.updatePosition(section);
        if (update < 1) {
            CommonResult.failed();
        }
        return CommonResult.success(null);
    }

    @PutMapping("/update")
    public CommonResult<Void> update(CmsSectionDetail sectionResult) {
        int update = sectionService.update(sectionResult);
        if (update < 1) {
            return CommonResult.failed(ResultEnum.UPDATE_CONTENT_FAILED);
        }
        return CommonResult.success(null, ResultEnum.UPDATE_CONTENT_SUCCESS.getMessage());
    }

}
