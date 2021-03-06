package cn.dapan.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Reflect {

    private Object object;
    private Class<?> clazz;
    private Class<?>[] parameterTypes;

    public Reflect(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Reflect(Class<?> clazz, Class<?>... parameterTypes) {
        this(clazz);
        this.parameterTypes = parameterTypes;
    }

    public static <T> Reflect inject(Class<T> clazz) {
        return new Reflect(clazz);
    }

    public static <T> Reflect inject(Class<T> clazz, Class<?>... parameterTypes) {
        return new Reflect(clazz, parameterTypes);
    }

    public <T> T object() {
        return (T) object;
    }

    public Reflect newInstance() {
        try {
            object = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Reflect newInstance(Object... args) {
        try {
            Class<?>[] parameterTypes = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                parameterTypes[i] = args[i].getClass();
            }

            Constructor constructor = null;
            try {
                constructor = clazz.getDeclaredConstructor(parameterTypes);
            } catch (NoSuchMethodException e) {
                parameterTypes = new Class[args.length];
                for (int i = 0; i < args.length; i++) {
                    if (guessRawType(parameterTypes, i, args[i])) continue;

                    parameterTypes[i] = args[i].getClass();
                    constructor = clazz.getDeclaredConstructor(parameterTypes);
                }
            }

            if (constructor != null) {
                object = constructor.newInstance(args);
            } else {
                System.out.println(constructor + " is null!");
            }
//            return (T) constructor.newInstance(args);
        }  catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return this;
    }

    private boolean guessRawType(Class<?>[] parameterTypes, int i, Object arg) {
        if (arg.getClass() == Character.class) {
            parameterTypes[i] = char.class;
            return true;
        }
        if (arg.getClass() == Byte.class) {
            parameterTypes[i] = byte.class;
            return true;
        }
        if (arg.getClass() == Short.class) {
            parameterTypes[i] = short.class;
            return true;
        }
        if (arg.getClass() == Integer.class) {
            parameterTypes[i] = int.class;
            return true;
        }
        if (arg.getClass() == Long.class) {
            parameterTypes[i] = long.class;
            return true;
        }
        if (arg.getClass() == Float.class) {
            parameterTypes[i] = float.class;
            return true;
        }
        if (arg.getClass() == Double.class) {
            parameterTypes[i] = double.class;
            return true;
        }
        if (arg.getClass() == Boolean.class) {
            parameterTypes[i] = boolean.class;
            return true;
        }
        return false;
    }

    public <R> R field(String key) {
        try {
            Field field = clazz.getDeclaredField(key);
            if (!field.isAccessible()) field.setAccessible(true);
            return (R) field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Reflect field(String key, Object arg) {
        try {
            Field field = clazz.getDeclaredField(key);
            if (!field.isAccessible()) field.setAccessible(true);
            field.set(object, arg);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return this;
    }

    public void method(String method, Object... args) {
        try {
            Class<?>[] parameterTypes = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                parameterTypes[i] = args[i].getClass();
            }
            Method declaredMethod = clazz.getDeclaredMethod(method, parameterTypes);
            if (!declaredMethod.isAccessible()) declaredMethod.setAccessible(true);
            declaredMethod.invoke(object, parameterTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
