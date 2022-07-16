package cn.ncepu.alpl.config;

import cn.hutool.core.util.RandomUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Random;

/**
 * @author xufuhang
 * @date 2022/4/27-1:17
 */
@Getter
@AllArgsConstructor
public enum RedisKeyPrefix {

    UMS_USER_PAGE("ums:user_page:", 60 * 60 * 24),
    UMS_USER_DETAIL("ums:user_detail:", 60 * 60 * 24),
    DMS_USER_COMMENT_STARS("dms:user_comment_stars:", 60 * 60 * 24),
    DMS_USER_COMMENT_REPLY_STARS("dms:user_comment_reply_stars:", 60 * 60 * 24),

    UMS_RESOURCE_LIST("ums:resource_list:", 60 * 60 * 24 * 15),
    UMS_OPTION_LIST("ums:option_list:", 60 * 60 * 24 * 15),

    CMS_MODULE_LIST("cms:module_list:", 60 * 60 * 24 * 15),

    CMS_CHAPTER_DETAIL_LIST("cms:chapter_detail_list:", 60 * 60 * 24 * 15),
    CMS_CHAPTER_LIST("cms:chapter_list:", 60 * 60 * 24),

    CMS_SECTION_LIST("cms:section_list:", 60 * 30),
    CMS_SECTION_DETAIL("cms:section_detail:", 60 * 60 * 24 * 15),

    DMS_COMMENT_DETAIL_PAGE("dms:comment_detail_page:", 60 * 60 * 24 * 15),
    DMS_COMMENT_REPLY_DETAIL_PAGE("dms:comment_reply_detail_page:", 60 * 60 * 24 * 15),
    ;


    /**
     * 键前缀
     */
    private final String keyPrefix;
    /**
     * 过期时间
     */
    private final int expire;

    public int getExpire() {
        return RandomUtil.randomInt(expire / 2) + expire;
    }

    public static int genRandomExpire(int expire) {
        return RandomUtil.randomInt(expire / 2) + expire;
    }
}
