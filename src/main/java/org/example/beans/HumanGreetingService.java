package org.example.beans;

import org.example.annotation.Bean;

@Bean("humanGreeting")
public class HumanGreetingService implements GreetingService {
    @Override
    public void sayHello() {
        System.out.println("Hey guys");
    }
}
