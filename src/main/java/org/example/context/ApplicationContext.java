package org.example.context;

import org.example.exception.NoSuchBeanException;
import org.example.exception.NoUniqueBeanException;

import java.util.Map;

public interface ApplicationContext {

    /**
     * Returns a bean by its type.
     *
     * @param beanType type of the desired bean
     * @param <T>      type of the desired bean
     * @return object of type T which represents desired bean
     * @throws NoSuchBeanException   if bean with provided type is not found
     * @throws NoUniqueBeanException if more than one bean with provided type is found
     */
    <T> T getBean(Class<T> beanType);

    /**
     * Returns a bean by its name and type.
     *
     * @param name     name of the desired bean
     * @param beanType type of the desired bean
     * @param <T>      type of the desired bean
     * @return object of type T which represents desired bean
     * @throws NoSuchBeanException   if bean with provided name and type is not found
     */
    <T> T getBean(String name, Class<T> beanType);

    /**
     * Returns a {@link Map} of all beans with the provided type where bean's name is a key and bean's instance is a value.
     *
     * @param beanType type of the desired bean
     * @param <T>      type of the desired bean
     * @return a {@link Map} of all beans with the provided type. If no beans are found returns empty {@link Map}
     */
    <T> Map<String, T> getAllBeans(Class<T> beanType);
}
