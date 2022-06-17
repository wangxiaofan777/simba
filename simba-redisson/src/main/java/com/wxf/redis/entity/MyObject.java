package com.wxf.redis.entity;

import com.wxf.redisson.RedissonManager;
import org.redisson.api.RLiveObjectService;
import org.redisson.api.annotation.REntity;
import org.redisson.api.annotation.RId;
import org.redisson.api.annotation.RIndex;
import org.redisson.api.condition.Conditions;

import java.util.Collection;

/**
 * 分布式实时对象
 *
 * @author WangMaoSong
 * @date 2022/6/17 13:50
 */
@REntity
public class MyObject {

    @RId
    private String id;

    @RIndex
    private String value;

    private MyObject Parent;

    public MyObject() {
    }

    public MyObject(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public MyObject getParent() {
        return Parent;
    }

    public void setParent(MyObject parent) {
        Parent = parent;
    }


    public static void test() {
        RLiveObjectService service = RedissonManager.getLiveObjectService();
        MyObject myObject = new MyObject();
        myObject.setId("1");
        // 将myObject对象当前状态持久化到Redis里并与之保持同步
//        myObject = service.persist(myObject);

        // 抛弃myObject对象当前的状态，并与Redis里的数据建利连接并保持同步

        MyObject myObject1 = new MyObject("1");
        myObject1 = service.attach(myObject1);

        // 将myObject对象当前的状态与Redis里的数据合并后并与之保持同步
        MyObject myObject2 = new MyObject("1");

        myObject2 = service.merge(myObject2);
        myObject2.setValue("1234");

        // 通过ID获取分布式实时对象
        MyObject myObject3 = service.get(MyObject.class, "1");
        System.out.println(myObject3);

        // 查找
        Collection<MyObject> value1 = service.find(MyObject.class, Conditions.in("value", "1234"));
        Collection<MyObject> value2 = service.find(MyObject.class, Conditions.and(Conditions.in("value", "1234")));
        System.out.println(value1);
        System.out.println(value2);


        // 注册耗时的对象
        service.registerClass(MyObject.class);
        // 注销对象
        service.unregisterClass(MyObject.class);
        // 是否注册对象
        service.isClassRegistered(MyObject.class);

    }

    public static void main(String[] args) {
        test();
    }
}
