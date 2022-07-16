package cn.ncepu.alpl.controller;

import cn.ncepu.alpl.api.CommonPage;
import cn.ncepu.alpl.api.CommonResult;
import cn.ncepu.alpl.api.ResultEnum;
import cn.ncepu.alpl.domain.DmsCommentDetail;
import cn.ncepu.alpl.exception.CustomRuntimeException;
import cn.ncepu.alpl.model.DmsComment;
import cn.ncepu.alpl.service.DmsCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


/**
 * @author xufuhang
 * @date 2022/3/29-15:23
 */
@RestController
@RequestMapping("/dms/comment")
@Slf4j
public class DmsCommentController {

    @Autowired
    DmsCommentService commentService;

    @PostMapping("/create")
    public CommonResult<Void> create(@RequestBody DmsComment comment, Principal principal) {
        int create = 0;
        try {
            create = commentService.create(comment, principal);
        } catch (CustomRuntimeException e) {
            return CommonResult.failed(e.getMessage());
        }
        if (create < 1) {
            log.error(comment.toString(), principal.toString());
            return CommonResult.failed(ResultEnum.ADD_CONTENT_FAILED.getMessage());
        }
        return CommonResult.success(null, ResultEnum.ADD_CONTENT_SUCCESS.getMessage());
    }

    @PostMapping("/create/stars")
    public CommonResult<Void> stars(DmsComment comment, Integer count, Principal principal) {
        int update = 0;
        try {
            update = commentService.stars(comment, count, principal);
        } catch (CustomRuntimeException e) {
            return CommonResult.failed(e.getMessage());
        }
        if (update < 1) {
            return CommonResult.failed();
        }
        return CommonResult.success();
    }

    @DeleteMapping("/delete/self")
    public CommonResult<Void> deleteSelf(Integer id, Principal principal) {
        int delete = commentService.deleteSelf(id, principal);
        if (delete < 1) {
            return CommonResult.failed(ResultEnum.DELETE_CONTENT_FAILED);
        }
        return CommonResult.success(null, ResultEnum.DELETE_CONTENT_SUCCESS.getMessage());
    }

    @DeleteMapping("/delete/any")
    public CommonResult<Void> deleteAny(Integer id) {
        int delete = commentService.deleteById(id);
        if (delete < 1) {
            return CommonResult.failed(ResultEnum.DELETE_CONTENT_FAILED);
        }
        return CommonResult.success(null, ResultEnum.DELETE_CONTENT_SUCCESS.getMessage());
    }

    @GetMapping("/query/list")
    public CommonResult<CommonPage<DmsCommentDetail>> fetchList(Principal principal,
                                                                Integer sectionId,
                                                                @RequestParam(defaultValue = "1") Integer pageNum,
                                                                @RequestParam(defaultValue = "5") Integer pageSize,
                                                                @RequestParam(defaultValue = "false") Boolean sortByStars,
                                                                @RequestParam(defaultValue = "false") Boolean sortByCreateTime) {
        CommonPage<DmsCommentDetail> page = commentService
                .fetchDetailList(principal, sectionId, pageNum, pageSize, sortByStars, sortByCreateTime);
        return CommonResult.success(page);
    }

    @PutMapping("/update")
    public CommonResult<Void> update(@RequestBody DmsComment comment) {
        int count = commentService.update(comment);
        if (count < 1) {
            return CommonResult.failed();
        }
        return CommonResult.success();
    }

    @PutMapping("/update/pinned")
    public CommonResult<Void> topping(Integer id) {
        int count = commentService.pinned(id);
        if (count < 1) {
            return CommonResult.failed();
        }
        return CommonResult.success();
    }

}
