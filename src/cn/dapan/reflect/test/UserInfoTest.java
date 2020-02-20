package cn.dapan.reflect.test;

import cn.dapan.reflect.Reflect;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

public class UserInfoTest {

    @Test
    public void reflect1() {
        Reflect.inject(UserInfo.class).newInstance(1, "王四").method("log");
    }

    @Test
    public void reflect2() {
        try {
            Constructor<UserInfo> constructor = UserInfo.class.getDeclaredConstructor(int.class, String.class);
            UserInfo userInfo = constructor.newInstance(1, "王四");
            Field field = UserInfo.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(userInfo, 10);
            System.out.println(field.get(userInfo));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        int id = Reflect.inject(UserInfo.class)
                .newInstance(1, "王四") // 创建对象，id=1,name=王四
                .field("id", 10)// 重新为 id 赋值
                .field("id"); // 获取 id 的值
        System.out.println(id);
    }
}