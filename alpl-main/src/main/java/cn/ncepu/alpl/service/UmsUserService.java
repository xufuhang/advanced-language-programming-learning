package cn.ncepu.alpl.service;

import cn.ncepu.alpl.api.CommonPage;
import cn.ncepu.alpl.domain.TokenResult;
import cn.ncepu.alpl.domain.UmsUserDetail;
import cn.ncepu.alpl.model.UmsRole;
import cn.ncepu.alpl.model.UmsUser;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public interface UmsUserService extends UserDetailsService {

    TokenResult login(String username, String password);

    UmsUserDetail query(Principal principal);

    CommonPage<UmsUser> queryList(int userPageNum, int fileClearQueryPageSize);

    CommonPage<UmsUser> queryList(Integer pageNum, Integer pageSize, String keyword, Integer roleId);

    List<UmsRole> queryRoleList(Integer id);

    @Transactional(rollbackFor = Exception.class)
    int allocRoleList(Integer userId, List<Integer> roleIdList);

    int update(UmsUser user);

    int delete(Integer id);

    /**
     * @param user 必须字段：用户名、密码、昵称。可选字段：邮箱
     * @return
     */
    UmsUser register(UmsUser user);

    UmsUser getUserByUsername(String username);

    /**
     * 查询某个用户点赞过的所有评论，返回这些评论的id集合
     * @param username 用户名
     * @return 评论的id集合
     */
    Set<Integer> getStarsCommentIdSetByUsername(String username);

    @Transactional(rollbackFor = Exception.class)
    Map<String, Integer> batchRegister(MultipartFile file);

    UmsUserDetail getUserDetailByUsername(String username);

    int uploadAvatar(Principal principal, MultipartFile file);
}
