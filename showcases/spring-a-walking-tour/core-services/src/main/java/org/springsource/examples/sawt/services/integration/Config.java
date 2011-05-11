package org.springsource.examples.sawt.services.integration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.integration.launch.JobLaunchingMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration("integrationConfiguration")
@ImportResource("/org/springsource/examples/sawt/services/batch/context.xml")
public class Config {

    private Log log = LogFactory.getLog(getClass());

    @Autowired
    private JobLauncher jobLauncher;

    @Bean
    public JobLaunchingMessageHandler jobMessageHandler() throws Exception {
        return new JobLaunchingMessageHandler(jobLauncher);
    }

}
