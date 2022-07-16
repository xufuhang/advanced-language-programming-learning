package cn.ncepu.alpl.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/*
@author xufuhang
@date 2022/3/27-18:51
*/
@Data
@AllArgsConstructor
public class TokenResult {

    private String token;
    private String tokenPrefix;
    private Long expires;

}
