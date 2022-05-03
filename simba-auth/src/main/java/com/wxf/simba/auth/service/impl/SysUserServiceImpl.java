package com.wxf.simba.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxf.simba.auth.entity.SysUser;
import com.wxf.simba.auth.mapper.SysUserMapper;
import com.wxf.simba.auth.service.SysUserService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统用户Service实现
 *
 * @author WangMaoSong
 * @date 2022/5/2 18:30
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {


    @CachePut(cacheNames = "sys_user", key = "#sysUser.id")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void saveSysUser(SysUser sysUser) {
        this.baseMapper.insert(sysUser);
    }

    @CacheEvict(cacheNames = "sys_user", key = "#sysUser.id")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void deleteSysUser(SysUser sysUser) {
        this.baseMapper.deleteById(sysUser);
    }

    @CacheEvict(cacheNames = "sys_user", key = "#sysUser.id")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void updateSysUser(SysUser sysUser) {
        this.baseMapper.updateById(sysUser);
    }

    @Override
    public List<SysUser> listSysUser() {
        return this.baseMapper.selectList(null);
    }

    @CachePut(cacheNames = "sys_user", key = "#id")
    @Override
    public SysUser getSysUserById(String id) {
        return this.baseMapper.selectById(id);
    }
}
