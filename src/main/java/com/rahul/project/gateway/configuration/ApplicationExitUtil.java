
package com.rahul.project.gateway.configuration;

import org.springframework.context.ConfigurableApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Rahul Malhotra
 * @since 1.0
 * Date 2019-05-21
 */
public abstract class ApplicationExitUtil {

    private ApplicationExitUtil() {
    }

    public static void waitForKeyPressToCleanlyExit(ConfigurableApplicationContext ctx) throws IOException {

        System.out.println("\nHit Enter to quit...");
        BufferedReader d = new BufferedReader(new InputStreamReader(System.in));
        d.readLine();

        ctx.stop();
        ctx.close();
    }
}