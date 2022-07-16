package cn.ncepu.alpl.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.ncepu.alpl.api.CommonPage;
import cn.ncepu.alpl.api.CacheKey;
import cn.ncepu.alpl.constant.StudentInfoName;
import cn.ncepu.alpl.dao.UmsUserDao;
import cn.ncepu.alpl.dao.UmsUserRoleRelationDao;
import cn.ncepu.alpl.domain.MinioUploadDto;
import cn.ncepu.alpl.domain.SecurityUser;
import cn.ncepu.alpl.domain.TokenResult;
import cn.ncepu.alpl.domain.UmsUserDetail;
import cn.ncepu.alpl.mapper.DmsUserCommentStarsRelationMapper;
import cn.ncepu.alpl.mapper.UmsResourceMapper;
import cn.ncepu.alpl.mapper.UmsUserMapper;
import cn.ncepu.alpl.mapper.UmsUserRoleRelationMapper;
import cn.ncepu.alpl.model.*;
import cn.ncepu.alpl.repostory.DmsCommentCacheService;
import cn.ncepu.alpl.repostory.DmsCommentReplyCacheService;
import cn.ncepu.alpl.repostory.UmsUserCacheService;
import cn.ncepu.alpl.service.FmsFileService;
import cn.ncepu.alpl.service.UmsRoleService;
import cn.ncepu.alpl.service.UmsUserService;
import cn.ncepu.alpl.util.JwtUtil;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.*;

/**
 *
 */
@Service
@Slf4j
public class UmsUserServiceImpl implements UmsUserService {

    @Autowired
    private UmsUserDao userDao;
    @Autowired
    private UmsResourceMapper umsResourceMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UmsUserMapper userMapper;
    @Autowired
    private UmsUserRoleRelationMapper userRoleRelationMapper;
    @Autowired
    private UmsUserRoleRelationDao userRoleRelationDao;
    @Autowired
    private UmsRoleService roleService;
    @Autowired
    private DmsUserCommentStarsRelationMapper userCommentStarsRelationMapper;
    @Autowired
    private UmsUserCacheService userCacheService;
    @Autowired
    private DmsCommentCacheService commentCacheService;
    @Autowired
    private DmsCommentReplyCacheService commentReplyCacheService;
    @Autowired
    private FmsFileService fileService;

    @Override
    public Map<String, Integer> batchRegister(MultipartFile file) {
        InputStream inputStream = null;
//        try {
//            inputStream = file.getInputStream();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String type = FileTypeUtil.getType(inputStream);

        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 读取文件并获取创建用户列表
        ExcelReader excelReader = ExcelUtil.getReader(inputStream);
        List<Map<String, Object>> rowList = excelReader.readAll();
        List<UmsUser> userList = new ArrayList<>();
        // 加密操作非常耗时，300用户加密密码并注册需要几十秒，而如果不加密，批量注册只需几秒
        String defaultPassword = passwordEncoder.encode("123456");
        for (Map<String, Object> row : rowList) {
            String studentId = (String) row.get(StudentInfoName.STUDENT_ID);
            String fullName = (String) row.get(StudentInfoName.STUDENT_NAME);
            String faculty = (String) row.get(StudentInfoName.FACULTY);
            String professionalName = (String) row.get(StudentInfoName.PROFESSIONAL_NAME);
            String currentLevel = (String) row.get(StudentInfoName.CURRENT_LEVEL);
            String studentStatus = (String) row.get(StudentInfoName.STUDENT_STATUS);

            UmsUser user = new UmsUser();
            user.setUsername(studentId);
            user.setPassword(defaultPassword);
            user.setNickname(fullName);
            user.setStatus((byte) 1);
            user.setCreateTime(new Date());

            userList.add(user);
        }

        // 处理重名，获取不重名的用户列表
        List<String> usernameList = new ArrayList<>();
        for (UmsUser user : userList) {
            usernameList.add(user.getUsername());
        }
        UmsUserExample example = new UmsUserExample();
        example.createCriteria().andUsernameIn(usernameList);
        List<UmsUser> existUserList = userMapper.selectByExample(example);
        Set<String> existUsernameSet = new HashSet<>();
        for (UmsUser user : existUserList) {
            existUsernameSet.add(user.getUsername());
        }
        List<UmsUser> insertList = new ArrayList<>();
        for (UmsUser user : userList) {
            if (!existUsernameSet.contains(user.getUsername())) {
                insertList.add(user);
            }
        }

        // 批量插入并清除缓存
        int total = userList.size();
        int count = 0;
        if (insertList.size() != 0) {
            count = userDao.insertList(insertList);
        }
        userCacheService.delAllUserPage();


        // 返回结果给前端
        Map<String, Integer> res = new HashMap<>();
        res.put("total", total);
        res.put("success", count);
        res.put("fail", total - count);
        return res;
    }

    @Override
    public UmsUser register(UmsUser user) {
        //查询是否有相同用户名的用户
        UmsUserExample example = new UmsUserExample();
        example.createCriteria().andUsernameEqualTo(user.getUsername());
        List<UmsUser> userList = userMapper.selectByExample(example);
        if (userList.size() > 0) {
            return null;
        }
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        if (StrUtil.isEmpty(user.getNickname())) {
            user.setNickname(user.getUsername());
        }
        Date createTime = new Date();
        user.setCreateTime(createTime);
        user.setStatus((byte) 1);
        userMapper.insert(user);

        userCacheService.delAllUserPage();
        return user;
    }

    @Override
    public int delete(Integer id) {
        int delete = userMapper.deleteByPrimaryKey(id);

        userCacheService.delAllUserPage();
        return delete;
    }

    /**
     * 调用getDetail获取详情，getDetail中会查询缓存
     */
    @Override
    public UmsUser getUserByUsername(String username) {
        UmsUser user = getUserDetailByUsername(username);
        return user;
    }

    @Override
    public UmsUserDetail getUserDetailByUsername(String username) {
        UmsUserDetail userDetail = userCacheService.getUserDetailByUsername(username);
        if (userDetail != null) {
            return userDetail;
        }

        userDetail = userDao.selectUserDetailByUsername(username);
        if (userDetail == null) {
            return null;
        }
        List<UmsRole> roleList = userDetail.getRoleList();
        List<UmsResource> resourceList = roleService.queryResourceByRoleIdList(roleList);
        userDetail.setResourceList(resourceList);
        List<UmsOption> optionList = roleService.queryOptionListByRoleIdList(roleList);
        userDetail.setOptionList(optionList);

        userCacheService.setUserDetailByUsername(userDetail);
        return userDetail;
    }

    @Override
    public TokenResult login(String username, String password) throws UsernameNotFoundException {
        UserDetails userDetails = loadUserByUsername(username);
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        setSecurityAuthentication(userDetails);
        String token = jwtUtil.generateToken(userDetails);
        TokenResult tokenResult = new TokenResult(token, jwtUtil.getTokenPrefix(), jwtUtil.getExpires());

        UmsUser user = getUserByUsername(userDetails.getUsername());
        UmsUser newUser = new UmsUser();
        newUser.setId(user.getId());

        Date loginTime = new Date();
        newUser.setLoginTime(loginTime);
//        userMapper.updateByPrimaryKeySelective(newUser);
        userDao.updateById(newUser);
        return tokenResult;
    }

    /**
     * 继承自Spring Security框架的接口，通过用户名加载用户权限相关的信息
     * @param username 用户名
     * @return 与用户权限相关的信息
     * @throws UsernameNotFoundException 用户名不存在
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UmsUserDetail userDetail = getUserDetailByUsername(username);
        if (userDetail == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        SecurityUser securityUser = new SecurityUser(userDetail);
        return securityUser;
    }

    @Override
    public CommonPage<UmsUser> queryList(int userPageNum, int fileClearQueryPageSize) {
        return queryList(userPageNum, fileClearQueryPageSize, null, null);
    }

    @Override
    public CommonPage<UmsUser> queryList(Integer pageNum, Integer pageSize, String keyword, Integer roleId) {
        CacheKey<String> cacheKey = new CacheKey<>();
        cacheKey.setKey(keyword + roleId);
        cacheKey.setPageNum(pageNum);
        cacheKey.setPageSize(pageSize);
        CommonPage<UmsUser> page = userCacheService.getUserList(cacheKey);
        if (page != null) {
            return page;
        }

        PageHelper.startPage(pageNum, pageSize);
        List<UmsUser> userList = userDao.selectByKeywordAndRoleName(keyword, roleId);
        page = CommonPage.genCommonPage(userList);

//        for (UmsUser user : page.getList()) {
//            user.setPassword(null);
//        }

        userCacheService.setUserPage(cacheKey, page);
        return page;
    }

    @Override
    public UmsUserDetail query(Principal principal) {
        UmsUserDetail userDetail = getUserDetailByUsername(principal.getName());

        if (userDetail == null) {
            return null;
        }
//        userDetail.setPassword(null);
        return userDetail;
    }

    /**
     * 没有使用缓存，id是查询条件，缓存是username作为查询条件
     */
    @Override
    public List<UmsRole> queryRoleList(Integer id) {
        List<UmsRole> roleList = userDao.selectRoleListByUserId(id);
        return roleList;
    }

    @Override
    public Set<Integer> getStarsCommentIdSetByUsername(String username) {
        UmsUser user = getUserByUsername(username);
        DmsUserCommentStarsRelationExample example = new DmsUserCommentStarsRelationExample();
        example.createCriteria().andUserIdEqualTo(user.getId());
        List<DmsUserCommentStarsRelation> relationList = userCommentStarsRelationMapper.selectByExample(example);
        Set<Integer> commentIdSet = new HashSet<>();
        for (DmsUserCommentStarsRelation relation : relationList) {
            Integer commentId = relation.getCommentId();
            commentIdSet.add(commentId);
        }
        return commentIdSet;
    }

    @Override
    public int allocRoleList(Integer userId, List<Integer> roleIdList) {
        int count = roleIdList == null ? 0 : roleIdList.size();
        //先删除原来的关系
        UmsUserRoleRelationExample example = new UmsUserRoleRelationExample();
        example.createCriteria().andUserIdEqualTo(userId);
        userRoleRelationMapper.deleteByExample(example);
        //建立新关系
        if (CollectionUtils.isEmpty(roleIdList)) {
            return 0;
        }
        List<UmsUserRoleRelation> list = new ArrayList<>();
        for (Integer roleId : roleIdList) {
            UmsUserRoleRelation userRoleRelation = new UmsUserRoleRelation();
            userRoleRelation.setRoleId(roleId);
            userRoleRelation.setUserId(userId);
            list.add(userRoleRelation);
        }
        int insert = userRoleRelationDao.insertList(list);

        userCacheService.delAllUserPage();
        return count;
    }

    @Override
    public int uploadAvatar(Principal principal, MultipartFile file) {
        String username = principal.getName();
        UmsUser user = getUserByUsername(username);

        MinioUploadDto uploadDto = fileService.uploadAvatar(file);
        String avatarUrl = uploadDto.getUrl();

        UmsUser updateUser = new UmsUser();
        updateUser.setId(user.getId());
        updateUser.setAvatar(avatarUrl);
        int count = userMapper.updateByPrimaryKeySelective(updateUser);

        afterUpdateUser(username);
        return count;
    }

    /**
     * 可能会更新username，此时需要删除原来username作为key的缓存，同时登出用户，
     * 因为token上的username被修改了，需要重新生成token
     * @param user 要更新的数据
     * @return 行数
     */
    @Override
    public int update(UmsUser user) {
        UmsUser oldUser = userMapper.selectByPrimaryKey(user.getId());

        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        int update = userMapper.updateByPrimaryKeySelective(user);

        afterUpdateUser(oldUser.getUsername());
        return update;
    }

    private void afterUpdateUser(String username) {
        userCacheService.delUserDetailByUsername(username);
        userCacheService.delAllUserPage();
        commentCacheService.delAllDetailPage();
        commentReplyCacheService.delAllDetailPage();
    }

    private void setSecurityAuthentication(UserDetails userDetails) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}
/*
$2a$10$HajWSuIuwEg9gI52Ti7zqeFxAtq/E67bj7tnTn.ifkyvMjygWeLK2
 */



