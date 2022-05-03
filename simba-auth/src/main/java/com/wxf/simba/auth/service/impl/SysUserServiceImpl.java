package com.wxf.simba.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxf.simba.auth.mapper.SysUserMapper;
import com.wxf.simba.auth.entity.SysUser;
import com.wxf.simba.auth.service.SysUserService;
import org.springframework.stereotype.Service;

/**
 * 系统用户Service实现
 *
 * @author WangMaoSong
 * @date 2022/5/2 18:30
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
}
