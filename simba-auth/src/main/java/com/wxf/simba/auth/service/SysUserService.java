package com.wxf.simba.auth.service;

import com.wxf.simba.auth.entity.SysUser;

import java.util.List;

/**
 * 系统用户Service
 *
 * @author WangMaoSong
 * @date 2022/5/2 18:29
 */
public interface SysUserService {

    void saveSysUser(SysUser sysUser);

    void deleteSysUser(SysUser sysUser);

    void updateSysUser(SysUser sysUser);

    List<SysUser> listSysUser();

    SysUser getSysUserById(String id);
}
