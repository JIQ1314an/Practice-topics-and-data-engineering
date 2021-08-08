package cn.edu.zut.lams.common.util;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author zhang
 * @date 2020/11/11 16:12
 */
@Component
public class DataAutoFill implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject meta) {
        this.strictInsertFill(meta, "createTime", Date.class, new Date());
        this.strictInsertFill(meta, "lastUpdateTime", Date.class, new Date());
    }

    @Override
    public void updateFill(MetaObject meta) {
        this.strictInsertFill(meta, "lastUpdateTime", Date.class, new Date());
    }

}
