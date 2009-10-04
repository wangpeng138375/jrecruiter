package com.hillert.camellos;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.ftplet.FtpException;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Starts the Spring Context and with that the class boots up Camel.
 *
 * @author Gunnar Hillert
 * @version $Id$
 *
 */
public class Main {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(Main.class);

    /**
     * Load the Spring Application Context and start the embedded FTP Server
     *
     * @param args
     * @throws Throwable
     */
    public static void main(String[] args) {

        final ApplicationContext classPathXmlApplicationContext =
                new ClassPathXmlApplicationContext("classpath:META-INF/spring/camel-context.xml");

        final FtpServer ftpServer =
            (FtpServer) classPathXmlApplicationContext.getBean("ftpServer");

        try {
            ftpServer.start();
        } catch (final FtpException e) {
            LOGGER.error("Error while starting up FTP Server.", e);
        }

    }
}
