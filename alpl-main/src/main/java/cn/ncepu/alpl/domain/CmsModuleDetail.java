package cn.ncepu.alpl.domain;

import cn.ncepu.alpl.model.CmsModule;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


/*
@author xufuhang
@date 2022/3/23-23:46
*/
@EqualsAndHashCode(callSuper = true)
@Data
public class CmsModuleDetail extends CmsModule {

    private List<CmsChapterDetail> chapters;

}
