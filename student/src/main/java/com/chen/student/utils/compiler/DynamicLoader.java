package com.chen.student.utils.compiler;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * author: JINCHENCHEN
 * date: 2020/12/31
 */
public class DynamicLoader {
    public static Map<String, byte[]> compile(String javaName, String javaSrc) {
        // 调用java编译器接口
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager stdManager = compiler
                .getStandardFileManager(null, null, null);

        try (MemoryJavaFileManager manager = new MemoryJavaFileManager(
                stdManager)) {

            @SuppressWarnings("static-access")
            JavaFileObject javaFileObject = manager.makeStringSource(javaName,
                    javaSrc);
            JavaCompiler.CompilationTask task = compiler.getTask(null, manager,
                    null, null, null, Arrays.asList(javaFileObject));
            if (task.call()) {
                return manager.getClassBytes();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 先根据类名在内存中查找是否已存在该类，若不存在则调用 URLClassLoader的 defineClass方法加载该类
     * URLClassLoader的具体作用就是将class文件加载到jvm虚拟机中去
     *
     * @author Administrator
     *
     */
    public static class MemoryClassLoader extends URLClassLoader {
        Map<String, byte[]> classBytes = new HashMap<String, byte[]>();

        public MemoryClassLoader(Map<String, byte[]> classBytes) {
            super(new URL[0], MemoryClassLoader.class.getClassLoader());
            this.classBytes.putAll(classBytes);
        }

        @Override
        protected Class<?> findClass(String name)
                throws ClassNotFoundException {
            byte[] buf = classBytes.get(name);
            if (buf == null) {
                return super.findClass(name);
            }
            classBytes.remove(name);
            return defineClass(name, buf, 0, buf.length);
        }
    }
}
