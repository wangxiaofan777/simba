package com.wxf.simba.auth.myabits;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.wxf.simba.auth.constants.SystemConstant;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Mybatis-plus全局字段填充
 *
 * @author WangMaoSong
 * @date 2022/5/2 16:08
 */
@Component
public class GlobalMetadataHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        String username = SystemConstant.ADMIN_USERNAME;
        this.strictInsertFill(metaObject, "createBy", String.class, username);
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, now);
        this.strictInsertFill(metaObject, "updateBy", String.class, username);
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, now);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        String username = SystemConstant.ADMIN_USERNAME;
        this.strictInsertFill(metaObject, "updateBy", String.class, username);
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }
}
