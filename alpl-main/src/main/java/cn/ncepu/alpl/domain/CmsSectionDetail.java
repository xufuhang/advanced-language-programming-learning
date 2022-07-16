package cn.ncepu.alpl.domain;

import cn.ncepu.alpl.model.CmsContent;
import cn.ncepu.alpl.model.CmsSection;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author xufuhang
 * @date 2022/4/9-10:04
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CmsSectionDetail extends CmsSection {

    private String content;

}
