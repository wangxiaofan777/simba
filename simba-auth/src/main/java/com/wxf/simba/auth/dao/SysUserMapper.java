package com.wxf.simba.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wxf.simba.auth.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户Mapper
 *
 * @author WangMaoSong
 * @date 2022/5/2 18:30
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}
