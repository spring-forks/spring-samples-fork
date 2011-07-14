package org.springsource.examples.sawt.factories;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) throws Throwable {
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:/org/springsource/examples/sawt/factories/config.xml");

    }
}
