package ru.otus.appcontainer;

import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.annotation.Annotation;
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
        for (Class clazz : configClasses) {
            checkConfigClass(clazz);

        }
        initAppComponentsContainerConfig(getAnnotatedClasses(configClasses));
    }

    private void initAppComponentsContainerConfig(Map<Annotation, List<Class>> annotatedClasses) {
        for (Annotation annotation : sortAnnotatedClasses(annotatedClasses)) {
            for (Class clazz : annotatedClasses.get(annotation)) {
                try {
                    createAppComponents(clazz.getConstructors()[0].newInstance(), (LinkedHashMap<Annotation, List<Method>>) getAnnotatedMethods(clazz.getMethods()));
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void createAppComponents(Object configObject, LinkedHashMap<Annotation, List<Method>> methodMap) throws IllegalAccessException, InvocationTargetException {
        for (Annotation annotation : sortAnnotatedMethods(methodMap)) {
            for (Method method : methodMap.get(annotation)) {
                Object[] args = new Object[method.getParameterCount()];
                for (int i = 0; i < method.getParameterCount(); i++) {
                    args[i] = getAppComponent(method.getParameterTypes()[i]);
                }
                Object configuredBean = method.invoke(configObject, args);
                appComponents.add(configuredBean);
                appComponentsByName.put(((AppComponent) annotation).name(), configuredBean);
            }
        }
    }

    private List<Annotation> sortAnnotatedMethods(Map<Annotation, List<Method>> methodMap) {
        return methodMap.keySet().stream().sorted((o1, o2) -> {
            if (o1 instanceof AppComponent
                    && o2 instanceof AppComponent) {
                return ((AppComponent) o1).order() - ((AppComponent) o2).order();
            }
            return 0;
        }).collect(Collectors.toList());
    }

    private List<Annotation> sortAnnotatedClasses(Map<Annotation, List<Class>> classesMap) {
        return classesMap.keySet().stream().sorted((o1, o2) -> {
            if (o1 instanceof AppComponentsContainerConfig
                    && o2 instanceof AppComponentsContainerConfig) {
                return ((AppComponentsContainerConfig) o1).order() - ((AppComponentsContainerConfig) o2).order();
            }
            return 0;
        }).collect(Collectors.toList());
    }

    private Map<Annotation, List<Method>> getAnnotatedMethods(Method[] methods) {
        LinkedHashMap<Annotation, List<Method>> methodMap = new LinkedHashMap<>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(AppComponent.class)) {
                if (methodMap.containsKey(method.getAnnotation(AppComponent.class))) {
                    methodMap.get(method.getAnnotation(AppComponent.class)).add(method);
                } else {
                    List<Method> methodsWithAnno = new ArrayList<>();
                    methodsWithAnno.add(method);
                    methodMap.put(method.getAnnotation(AppComponent.class), methodsWithAnno);
                }
            }
        }
        return methodMap;
    }

    private Map<Annotation, List<Class>> getAnnotatedClasses(Set<Class<?>> classes) {
        LinkedHashMap<Annotation, List<Class>> classesMap = new LinkedHashMap<>();
        for (Class clazz : classes) {
            if (clazz.isAnnotationPresent(AppComponentsContainerConfig.class)) {
                if (classesMap.containsKey(clazz.getAnnotation(AppComponentsContainerConfig.class))) {
                    classesMap.get(clazz.getAnnotation(AppComponentsContainerConfig.class)).add(clazz);
                } else {
                    List<Class> classesWithAnno = new ArrayList<>();
                    classesWithAnno.add(clazz);
                    classesMap.put(clazz.getAnnotation(AppComponentsContainerConfig.class), classesWithAnno);
                }
            }
        }
        return classesMap;
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
