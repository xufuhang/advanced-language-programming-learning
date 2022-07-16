package cn.ncepu.alpl.domain;

import cn.ncepu.alpl.model.CmsChapter;
import cn.ncepu.alpl.model.CmsSection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
@author xufuhang
@date 2022/3/23-23:48
*/
@EqualsAndHashCode(callSuper = true)
@Data
public class CmsChapterDetail extends CmsChapter {

    private List<CmsSection> sections;

}
