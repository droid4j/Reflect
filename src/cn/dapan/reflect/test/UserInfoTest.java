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
        Reflect instance = Reflect.inject(UserInfo.class).newInstance(1, "王四");
        instance.field("id", 10);
        int id = instance.field("id");
        System.out.println(id);
    }
}