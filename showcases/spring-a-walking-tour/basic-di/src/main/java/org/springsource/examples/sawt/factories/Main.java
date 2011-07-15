package org.springsource.examples.sawt.factories;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) throws Throwable {
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/org/springsource/examples/sawt/factories/config.xml");

        AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext(Config.class) ;

    }
}
