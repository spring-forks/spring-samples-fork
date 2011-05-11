package org.springsource.examples.sawt.annotations;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * All except the DS is inferred.
 */
public class Main {

    public static void main(String[] args) throws Throwable {
      ApplicationContext ac=  new ClassPathXmlApplicationContext(
        		"classpath:/org/springsource/examples/sawt/annotations/config.xml");
    }
}
