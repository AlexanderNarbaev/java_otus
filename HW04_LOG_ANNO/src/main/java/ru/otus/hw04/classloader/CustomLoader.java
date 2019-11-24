package ru.otus.hw04.classloader;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;

public class CustomLoader {
    public static SampleClass createInterfaceInstance() {
        ClassLoader classLoader = CustomLoader.class.getClassLoader();
        InvocationHandler handler = new CustomInvocationHandler(new SampleClassImpl());
        return (SampleClass) Proxy.newProxyInstance(classLoader,
                new Class<?>[]{SampleClass.class}, handler);
    }

    static class CustomInvocationHandler implements InvocationHandler {

        private SampleClassImpl sampleClass;
        private ArrayList<Method> annotatedMethods = new ArrayList<>();

        public CustomInvocationHandler(SampleClassImpl sampleClass) {
            this.sampleClass = sampleClass;
            for (Method method : sampleClass.getClass().getMethods()) {
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
