package ru.otus.hw04.classloader;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class CustomLoader {
    public static SampleClass createInterfaceInstance() {
        ClassLoader classLoader = CustomLoader.class.getClassLoader();
        for (Method method : SampleClassImpl.class.getMethods()) {
            if (method.isAnnotationPresent(Log.class)) {
                InvocationHandler handler = new CustomInvocationHandler(new SampleClassImpl());
                return (SampleClass) Proxy.newProxyInstance(classLoader,
                        new Class<?>[]{SampleClass.class}, handler);
            }
        }
        return new SampleClassImpl();
    }

    static class CustomInvocationHandler implements InvocationHandler {

        private SampleClassImpl sampleClass;

        public CustomInvocationHandler(SampleClassImpl sampleClass) {
            this.sampleClass = sampleClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.print("invoking method:" + method + "with param's:\t");
            int i = 1;
            for (Object param : args) {
                System.out.print(i + " param type of " + param.getClass() + " with value " + param + ";\t");
                i++;
            }
            System.out.println();
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
