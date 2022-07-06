package org.example.context;

public record BeanDefinition(String name, Class<?> type, Object instance) {
}
