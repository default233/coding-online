package com.chen.admin.utils.compiler;

import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author danger
 * @date 2021/4/7
 */
@Component
public class CompileUtils {
    public static void testInvoke(String className, String source)
            throws ClassNotFoundException, IllegalAccessException,
            InstantiationException, NoSuchMethodException,
            InvocationTargetException {

        final String SUFFIX = ".java";// 类名后面要跟的后缀

        // 对source进行编译生成class文件存放在Map中，这里用bytecode接收
        Map<String, byte[]> bytecode = DynamicLoader.compile(className + SUFFIX,
                source);

        // 加载class文件到虚拟机中，然后通过反射执行
        @SuppressWarnings("resource")
        DynamicLoader.MemoryClassLoader classLoader = new DynamicLoader.MemoryClassLoader(
                bytecode);
        Class<?> clazz = classLoader.loadClass(className);
//        Object object = clazz.newInstance();

//        // 得到sayHello方法
//        Method sayHelloMethod = clazz.getMethod("sayHello", String.class);
//        sayHelloMethod.invoke(object, "This is the method called by reflect");
//
//        // 得到add方法
//        Method addMethod = clazz.getMethod("add", int.class, int.class);
//        Object returnValue = addMethod.invoke(object, 1024, 1024);
//        System.out.println(Thread.currentThread().getName() + ": "
//                + "1024 + 1024 = " + returnValue);

        // 因为在main方法中，调用了add和sayHello方法，所以直接调用main方法就可以执行两个方法
        Method mainMethod = clazz.getDeclaredMethod("main", String[].class);

        Examination.start();
//        try {
//            Thread.sleep(100_000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        mainMethod.invoke(null, (Object) new String[] {});
        Map<String, String> map = new HashMap<>(160);

        Examination.end();

    }
}
