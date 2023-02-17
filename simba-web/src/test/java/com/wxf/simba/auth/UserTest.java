package com.wxf.simba.auth;

import com.alibaba.fastjson.JSONObject;
import com.wxf.simba.auth.entity.SysUser;
import com.wxf.simba.auth.service.SysUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author WangMaoSong
 * @date 2022/5/3 19:06
 */
@SpringBootTest
public class UserTest {

    @Autowired
    private SysUserService sysUserService;

    @Test
    public void test01() {
        SysUser sysUser = SysUser.builder()
                .username("admin")
                .password("admin")
                .age(18)
                .gender(0)
                .version(1)
                .build();
        this.sysUserService.saveSysUser(sysUser);
    }


    @Test
    public void test02() {
        this.sysUserService.deleteSysUser(SysUser.builder().id("1521451577300942849").build());
    }

    @Test
    public void test03() {
        SysUser sysUser = SysUser.builder()
                .id("1521449978960789505")
                .username("admin")
                .password("123456")
                .age(18)
                .gender(0)
                .version(2)
                .build();
        this.sysUserService.updateSysUser(sysUser);
    }

    @Test
    public void test04() {
        System.out.println(JSONObject.toJSONString(this.sysUserService.listSysUser()));
    }


    @Test
    public void test05() {
        System.out.println(JSONObject.toJSONString(this.sysUserService.getSysUserById("1521449978960789505")));
    }

}
