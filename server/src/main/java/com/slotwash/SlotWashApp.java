package com.slotwash;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableConfigurationProperties({
    QuartzProperties.class
})
@EnableAspectJAutoProxy
public class SlotWashApp {

    private static final Logger log = LoggerFactory.getLogger(SlotWashApp.class);

    @Autowired private Environment environment;

    private static ConfigurableApplicationContext context;


    @PostConstruct
    public void initApplication(){
        log.info("Running with Spring profile(s) : {}", Arrays.toString(environment.getActiveProfiles()));
        Collection<String> activeProfiles = Arrays.asList(environment.getActiveProfiles());
        
        if(activeProfiles.contains(Constants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(Constants.SPRING_PROFILE_PRODUCTION)){
            log.error("You must misconfigured your application! it should not run with both the 'dev' and 'prod' profiles at the same time");
        }
    }


    public static void main(String[] args) throws UnknownHostException {

        SpringApplication app = new SpringApplication(SlotWashApp.class);
        context = app.run(args);

        Environment environment = context.getEnvironment();

        log.info(
        "\n"
            + "----------------------------------------------------------\n"
            + "\tApplication '{}' is running! Access URLs:\n"
            + "\tLocal: \t\thttp://127.0.0.1:{}\n"
            + "\tExternal: \thttp://{}:{}\n"
            + "----------------------------------------------------------",
        environment.getProperty("spring.application.name"),
        environment.getProperty("server.port"),
        InetAddress.getLocalHost().getHostAddress(),
        environment.getProperty("server.port"));
    }


    public static void restart() {
    ApplicationArguments args = context.getBean(ApplicationArguments.class);

    Thread thread =
        new Thread(
            () -> {
              try {
                Thread.sleep(1000);
              } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
              }
              context.close();
              SpringApplication app = new SpringApplication(SlotWashApp.class);
              context = app.run(args.getSourceArgs());
              log.info(
                  "\n----------------------------------------------------------\n"
                      + "\tRestarted application"
                      + "\n----------------------------------------------------------");
            });

    thread.setDaemon(false);
    thread.start();
    log.info("Finished restart");
  }


    
}
