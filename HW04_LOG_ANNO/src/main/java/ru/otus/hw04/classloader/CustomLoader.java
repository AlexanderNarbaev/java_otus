package ru.otus.hw04.classloader;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;

public class CustomLoader {
    public static SampleClass createInstance(Class<? extends SampleClass> clazz) {
        ClassLoader classLoader = clazz.getClassLoader();
        InvocationHandler handler = new CustomInvocationHandler(clazz);
        return (SampleClass) Proxy.newProxyInstance(classLoader,
                new Class<?>[]{SampleClass.class}, handler);
    }

    static class CustomInvocationHandler implements InvocationHandler {

        private SampleClass sampleClass;
        private ArrayList<Method> annotatedMethods = new ArrayList<>();

        public CustomInvocationHandler(Class<? extends SampleClass> clazz) {
            try {
                this.sampleClass = clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            for (Method method : clazz.getMethods()) {
                if (method.isAnnotationPresent(Log.class)) {
                    annotatedMethods.add(method);
                }
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            boolean annotationMethod = false;
            for (Method annoMeth : annotatedMethods) {
                if (annoMeth.getName().equalsIgnoreCase(method.getName())
                        && Arrays.deepEquals(annoMeth.getParameterTypes(), method.getParameterTypes())) {
                    annotationMethod = true;
                }
            }
            if (annotationMethod) {
                System.out.print("invoking method:" + method + "with param's:\t");
                int i = 1;
                for (Object param : args) {
                    System.out.print(i + " param type of " + param.getClass() + " with value " + param + ";\t");
                    i++;
                }
                System.out.println();
            }
            return method.invoke(sampleClass, args);
        }

        @Override
        public String toString() {
            return "CustomInvocationHandler{" +
                    "myClass=" + sampleClass +
                    '}';
        }
    }

}
