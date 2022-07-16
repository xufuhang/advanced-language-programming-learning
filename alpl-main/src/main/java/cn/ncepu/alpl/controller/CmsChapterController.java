package cn.ncepu.alpl.controller;

import cn.ncepu.alpl.api.CommonResult;
import cn.ncepu.alpl.domain.CmsChapterDetail;
import cn.ncepu.alpl.model.CmsChapter;
import cn.ncepu.alpl.service.CmsChapterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
@author xufuhang
@date 2022/3/13-17:58
*/
@RestController
@RequestMapping("/cms/chapter")
@Slf4j
public class CmsChapterController {

    @Autowired
    CmsChapterService chapterService;

    @PostMapping("/create")
    public CommonResult<Void> create(CmsChapter chapter, Principal principal) {
        int count = chapterService.create(chapter, principal);
        if (count < 1) {
            return CommonResult.failed();
        }
        return CommonResult.success();
    }

    @DeleteMapping("/delete")
    public CommonResult<Void> delete(Integer id) {
        int delete = chapterService.delete(id);
        if (delete < 1) {
            return CommonResult.failed();
        }
        return CommonResult.success();
    }

    @GetMapping("/query/list")
    public CommonResult<List<CmsChapter>> fetchList(CmsChapter chapter) {
        List<CmsChapter> list = chapterService.queryByCondition(chapter);
        return CommonResult.success(list);
    }

    @GetMapping("/query/detail/list")
    public CommonResult<List<CmsChapterDetail>> fetchCatalog(Integer moduleId) {
        List<CmsChapterDetail> cmsChapterDetailList = chapterService.queryChapterDetailListByModuleId(moduleId);
        return CommonResult.success(cmsChapterDetailList);
    }

    @PutMapping("/update/position")
    public CommonResult<Void> updatePosition(CmsChapter chapter) {
        int update = chapterService.updatePosition(chapter);
        if (update < 1) {
            return CommonResult.failed();
        }
        return CommonResult.success();
    }

    @PutMapping("/update")
    public CommonResult<Void> update(CmsChapter chapter) {
        int update = chapterService.updateByIdSelective(chapter);
        if (update < 1) {
            return CommonResult.failed();
        }
        return CommonResult.success();
    }

}
