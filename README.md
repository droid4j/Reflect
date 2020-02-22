# ~~Reflect~~
采用链式调用封装java反射操作

```java
int id = Reflect.inject(UserInfo.class) // 对 UserInfo.class 进行操作
                .newInstance(1, "王四") // 创建对象，id=1,name=王四
                .field("id", 10)// 重新为 id 赋值
                .field("id"); // 获取 id 的值
System.out.println(id);
```

使用gradle重建工程，方便引入：https://github.com/droid4j/reflectLib
