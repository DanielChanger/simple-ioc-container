package org.example.beans;

import org.example.annotation.Bean;

@Bean
public class GeniusGreetingService implements GreetingService {
    @Override
    public void sayHello() {
        System.out.println("Hello world!");
    }
}
