/*
 * Copyright 2006-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springsource.greenbeans.examples.edawithspring.etailer.backoffice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * this class instantiates the Spring Integration flow
 *
 * @author Josh Long
 *
 */
public class Main {

  static private Log log = LogFactory.getLog(Main.class ) ;

  public static void main(String [] args ) throws Exception  {

    ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("/backoffice.xml") ;
    classPathXmlApplicationContext.start();

    log.debug("just launched the backoffice...");

  }
}
