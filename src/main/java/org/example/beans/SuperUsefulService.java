package org.example.beans;

import org.example.annotation.Bean;

@Bean
public class SuperUsefulService {

    public SuperUsefulService() {
        System.out.println("SuperUsefulService is up to your service, sir");
    }

    public void doNothing() {
        // AT ALL
    }
}
