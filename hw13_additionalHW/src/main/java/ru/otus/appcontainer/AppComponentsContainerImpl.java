package ru.otus.appcontainer;

import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(String packageName) {
        this(new Reflections(packageName,
                new TypeAnnotationsScanner()).getTypesAnnotatedWith(AppComponentsContainerConfig.class, true));
    }

    public AppComponentsContainerImpl(Set<Class<?>> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Set<Class<?>> configClasses) {
        for (Class<?> clazz : configClasses) {
            checkConfigClass(clazz);
        }
        initAppComponentsContainerConfig(getAnnotatedClasses(configClasses));
    }

    private void initAppComponentsContainerConfig(Set<Class<?>> annotatedClasses) {
        for (Class<?> clazz : sortAnnotatedClasses(annotatedClasses)) {
            try {
                createAppComponents(clazz.getConstructors()[0].newInstance(), getAnnotatedMethods(clazz.getMethods()));
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

    }

    private void createAppComponents(Object configObject, Set<Method> methodSet) throws IllegalAccessException,
            InvocationTargetException {
        for (Method method : sortAnnotatedMethods(methodSet)) {
            Object[] args = new Object[method.getParameterCount()];
            for (int i = 0; i < method.getParameterCount(); i++) {
                args[i] = getAppComponent(method.getParameterTypes()[i]);
            }
            Object configuredBean = method.invoke(configObject, args);
            appComponents.add(configuredBean);
            appComponentsByName.put(method.getAnnotation(AppComponent.class).name(), configuredBean);
        }
    }

    private List<Method> sortAnnotatedMethods(Set<Method> methodSet) {
        return methodSet.stream().sorted((o1, o2) -> {
            if (o1.getAnnotation(AppComponent.class) != null
                    && o2.getAnnotation(AppComponent.class) != null) {
                return o1.getAnnotation(AppComponent.class).order() - o2.getAnnotation(AppComponent.class).order();
            }
            return 0;
        }).collect(Collectors.toList());
    }

    private List<Class<?>> sortAnnotatedClasses(Set<Class<?>> classesMap) {
        return classesMap.stream().sorted((o1, o2) -> {
            if (o1.getAnnotation(AppComponentsContainerConfig.class) != null
                    && o2.getAnnotation(AppComponentsContainerConfig.class) != null) {
                return o1.getAnnotation(AppComponentsContainerConfig.class).order()
                        - o1.getAnnotation(AppComponentsContainerConfig.class).order();
            }
            return 0;
        }).collect(Collectors.toList());
    }

    private Set<Method> getAnnotatedMethods(Method[] methods) {
        Set<Method> methodSet = new HashSet<>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(AppComponent.class)) {
                methodSet.add(method);
            }
        }
        return methodSet;
    }

    private Set<Class<?>> getAnnotatedClasses(Set<Class<?>> classes) {
        Set<Class<?>> filteredClasses = new HashSet<>();
        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(AppComponentsContainerConfig.class)) {
                filteredClasses.add(clazz);
            }
        }
        return filteredClasses;
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        for (Object appComponent : appComponents) {
            if (componentClass.isInstance(appComponent)) {
                return componentClass.cast(appComponent);
            }
        }
        return null;
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        if (appComponentsByName.containsKey(componentName)) {
            return (C) appComponentsByName.get(componentName);
        }
        return null;
    }
}
