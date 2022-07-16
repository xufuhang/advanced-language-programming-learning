package cn.ncepu.alpl.constant;

/**
 * @author xufuhang
 * @date 2022/5/18-0:49
 */
public interface FileClearConstant {

//    String FILE_NAME_REG_EXP = "\\[.{0,100}?\\]\\(.{0,100}?([^/]{1,200}?)\\)";
//    int FILE_NAME_PATTERN_GROUP = 1;
    String FILE_NAME_REG_EXP = "(?<=(data-link=\"[^\"]{1,200}?|src=\"[^\"]{1,200}?))[^/\"]{1,50}?(?=\")";
    int FILE_NAME_PATTERN_GROUP = 0;
}
