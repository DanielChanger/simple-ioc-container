package org.example.context;

import lombok.SneakyThrows;
import org.example.annotation.Bean;
import org.example.exception.NoSuchBeanException;
import org.example.exception.NoUniqueBeanException;
import org.reflections.Reflections;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Scans all packages from the root for classes marked as {@link Bean} and adds them into context,
 * which is a simple {@link Map} of bean's name as key and bean's instance as value.
 */
public class SimpleApplicationContext implements ApplicationContext {

    private static final String NULL_BEAN_TYPE_EXCEPTION_MSG = "Bean type must not be null";
    private static final String NULL_BEAN_NAME_EXCEPTION_MSG = "Bean name must not be null";

    Map<String, BeanDefinition> beansMap;

    public SimpleApplicationContext(String beansRootPackage) {
        Set<Class<?>> types = new Reflections(beansRootPackage).getTypesAnnotatedWith(Bean.class);
        beansMap = types.stream()
                        .map(this::mapToBeanDefinition)
                        .collect(Collectors.toMap(BeanDefinition::name, Function.identity()));

        beansMap.forEach((k, v) -> System.out.printf("'%s' bean of %s is up%n", k, v.type()));
    }

    private BeanDefinition mapToBeanDefinition(Class<?> beanType) {
        String name = getBeanName(beanType);
        Object instance = createBeanInstance(beanType);
        return new BeanDefinition(name, beanType, instance);
    }

    private static String getBeanName(Class<?> beanType) {
        String name = beanType.getAnnotation(Bean.class).value();
        if (name.isEmpty()) {
            String typeName = beanType.getSimpleName();
            String firstSymbol = String.valueOf(typeName.charAt(0));
            return typeName.replaceFirst(firstSymbol, firstSymbol.toLowerCase());
        }
        return name;
    }

    @SneakyThrows
    private static Object createBeanInstance(Class<?> beanType) {
        return beanType.getConstructor().newInstance();
    }

    @Override
    public <T> T getBean(Class<T> beanType) {
        Objects.requireNonNull(beanType, NULL_BEAN_TYPE_EXCEPTION_MSG);
        var beans = beansMap.values()
                            .stream()
                            .filter(bean -> beanType.isAssignableFrom(bean.type()))
                            .toList();

        if (beans.isEmpty()) {
            throw new NoSuchBeanException(
                    "No bean with '%s' type is found in application context".formatted(beanType.getName()));
        }
        if (beans.size() > 1) {
            throw new NoUniqueBeanException("There are several beans with type '%s'".formatted(beanType));
        }

        return beanType.cast(beans.get(0).instance());
    }

    @Override
    public <T> T getBean(String name, Class<T> beanType) {
        Objects.requireNonNull(name, NULL_BEAN_NAME_EXCEPTION_MSG);
        Objects.requireNonNull(beanType, NULL_BEAN_TYPE_EXCEPTION_MSG);
        BeanDefinition bean = beansMap.get(name);
        if (bean == null) {
            throw new NoSuchBeanException("No bean with '%s' name is found in application context".formatted(name));
        }
        if (beanType.isAssignableFrom(bean.type())) {
            return beanType.cast(bean.instance());
        }
        throw new NoSuchBeanException(
                "Bean with '%s' name is of '%s' type, not the '%s' provided type"
                        .formatted(bean.name(), bean.type(), beanType)
        );
    }

    @Override
    public <T> Map<String, T> getAllBeans(Class<T> beanType) {
        Objects.requireNonNull(beanType, NULL_BEAN_TYPE_EXCEPTION_MSG);
        return beansMap.entrySet()
                       .stream()
                       .filter(entry -> beanType.isAssignableFrom(entry.getValue().type()))
                       .collect(Collectors.toMap(Map.Entry::getKey, entry -> beanType.cast(entry.getValue().instance())));
    }
}
