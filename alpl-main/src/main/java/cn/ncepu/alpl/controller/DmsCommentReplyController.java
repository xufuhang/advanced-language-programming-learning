package cn.ncepu.alpl.controller;

import cn.ncepu.alpl.api.CommonPage;
import cn.ncepu.alpl.api.CommonResult;
import cn.ncepu.alpl.api.ResultEnum;
import cn.ncepu.alpl.domain.DmsCommentReplyDetail;
import cn.ncepu.alpl.exception.CustomRuntimeException;
import cn.ncepu.alpl.model.DmsCommentReply;
import cn.ncepu.alpl.service.DmsCommentReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
@author xufuhang
@date 2022/4/4-17:47
*/
@RestController
@RequestMapping("/dms/commentReply")
public class DmsCommentReplyController {

    @Autowired
    DmsCommentReplyService commentReplyService;

    @PostMapping("/create")
    public CommonResult<Void> create(@RequestBody DmsCommentReply commentReply,
                                     Principal principal) {
        int create = commentReplyService.create(commentReply, principal);
        if (create < 1) {
            return CommonResult.failed(ResultEnum.ADD_CONTENT_FAILED);
        }
        return CommonResult.success(null, ResultEnum.ADD_CONTENT_SUCCESS.getMessage());
    }

    @PostMapping("/create/stars")
    public CommonResult<Void> stars(DmsCommentReply reply, Integer count, Principal principal) {
        int update = 0;
        try {
            update = commentReplyService.stars(reply, count, principal);
        } catch (CustomRuntimeException e) {
            return CommonResult.failed(e.getMessage());
        }
        if (update < 1) {
            return CommonResult.failed();
        }
        return CommonResult.success();
    }

    @DeleteMapping("/delete/any")
    public CommonResult<Void> deleteAny(Integer id){
        int delete = commentReplyService.delete(id);
        if (delete < 1) {
            return CommonResult.failed(ResultEnum.DELETE_CONTENT_FAILED);
        }
        return CommonResult.success(null, ResultEnum.DELETE_CONTENT_SUCCESS.getMessage());
    }

    @DeleteMapping("/delete/self")
    public CommonResult<Void> deleteSelf(Integer id, Principal principal){
        int delete = commentReplyService.deleteSelf(id, principal);
        if (delete < 1) {
            return CommonResult.failed(ResultEnum.DELETE_CONTENT_FAILED);
        }
        return CommonResult.success(null, ResultEnum.DELETE_CONTENT_SUCCESS.getMessage());
    }

    @GetMapping("/query/list")
    public CommonResult<CommonPage<DmsCommentReplyDetail>> fetchCommentReplyList(Integer commentId,
                          @RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "5") Integer pageSize,
                          @RequestParam(defaultValue = "false") Boolean sortByStars,
                          @RequestParam(defaultValue = "false") Boolean sortByCreateTime,
                          Principal principal) {
        CommonPage<DmsCommentReplyDetail> page = commentReplyService
                .selectPageBySectionId(commentId, pageNum, pageSize, sortByStars, sortByCreateTime, principal);
        if (page == null) {
            CommonResult.failed(ResultEnum.QUERY_CONTENT_FAILED);
        }
        return CommonResult.success(page);
    }

    @PutMapping("/update")
    public CommonResult<Void> update(@RequestBody DmsCommentReply reply) {
        int count = commentReplyService.update(reply);
        if (count < 1) {
            return CommonResult.failed();
        }
        return CommonResult.success();
    }

    @PutMapping("/update/pinned")
    public CommonResult<Void> topping(Integer id) {
        int count = commentReplyService.topping(id);
        if (count < 1) {
            return CommonResult.failed();
        }
        return CommonResult.success();
    }

}
