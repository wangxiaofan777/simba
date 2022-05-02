package com.wxf.simba.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 系统用户
 *
 * @author WangMaoSong
 * @date 2022/5/2 15:46
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("simba_sys_user")
public class SysUser extends BaseModel implements Serializable {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 年龄
     */
    private String age;

    /**
     * 性别
     */
    private String gender;

    /**
     * 备注
     */
    private String remark;

}
