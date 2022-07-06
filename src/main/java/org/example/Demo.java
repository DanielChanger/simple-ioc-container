package org.example;

import org.example.beans.GeniusGreetingService;
import org.example.beans.GreetingService;
import org.example.beans.HumanGreetingService;
import org.example.beans.NonBeanService;
import org.example.beans.SuperUsefulService;
import org.example.context.ApplicationContext;
import org.example.exception.NoSuchBeanException;
import org.example.exception.NoUniqueBeanException;
import org.example.context.SimpleApplicationContext;


public class Demo {
    private static final String BEAN_ROOT_PACKAGE = "org.example.beans";

    public static void main(String[] args) {
        ApplicationContext context = new SimpleApplicationContext(BEAN_ROOT_PACKAGE);

        // Simple bean retrieval
        SuperUsefulService superUsefulService = context.getBean(SuperUsefulService.class);
        superUsefulService.doNothing();

        // fails because NonBeanService is not a bean
        try {
            context.getBean(NonBeanService.class);
        } catch (NoSuchBeanException e) {
            System.out.println(e.getMessage());
        }

        // fails because more than one bean exists with GreetingService type
        try {
            context.getBean(GreetingService.class);
        } catch (NoUniqueBeanException e) {
            System.out.println(e.getMessage());
        }

        // Getting beans of the same parent type separately for now
        GreetingService humanGreetingService = context.getBean(HumanGreetingService.class);
        GreetingService geniusGreetingService = context.getBean(GeniusGreetingService.class);
        humanGreetingService.sayHello();
        geniusGreetingService.sayHello();

        // Getting beans of the same parent type in one map
        var allGreetingBeans = context.getAllBeans(GreetingService.class);
        System.out.println(allGreetingBeans);

        // returns empty map on non-existing bean with provided type
        var allBeans = context.getAllBeans(NonBeanService.class);
        System.out.println(allBeans);

        // get bean by custom name and type
        context.getBean("humanGreeting", GreetingService.class);
        // get bean by default name and type
        context.getBean("geniusGreetingService", GreetingService.class);
        // get bean by default name and type
        context.getBean("geniusGreetingService", GreetingService.class);

        // fails because no bean with provided name exists
        try {
            context.getBean("nonExistingBeanName", GreetingService.class);
        } catch (NoSuchBeanException e) {
            System.out.println(e.getMessage());
        }

        // fails because bean with provided name doesn't have the same type as provided
        try {
            context.getBean("humanGreeting", GeniusGreetingService.class);
        } catch (NoSuchBeanException e) {
            System.out.println(e.getMessage());
        }
    }
}
