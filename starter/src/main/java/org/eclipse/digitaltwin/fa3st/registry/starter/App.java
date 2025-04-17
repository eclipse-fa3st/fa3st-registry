/**
 * Copyright (c) 2025 the Eclipse FA³ST Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.eclipse.digitaltwin.fa3st.registry.starter;

import static org.eclipse.digitaltwin.fa3st.registry.starter.App.APP_NAME;

import ch.qos.logback.classic.Level;
import java.io.PrintStream;
import org.eclipse.digitaltwin.fa3st.registry.starter.logging.Fa3stFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;


/**
 * Main application of the registry.
 */
@SpringBootApplication
@EntityScan(basePackages = {
        "org.eclipse.digitaltwin.fa3st.common.model.descriptor"
})
@ImportResource("classpath:applicationContext.xml")
@Command(name = APP_NAME, mixinStandardHelpOptions = true, description = "Starts a FA³ST Registry", usageHelpAutoWidth = true)
public class App implements Runnable {
    protected static final String APP_NAME = "FA³ST Registry Starter";
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    @Option(names = {
            "-q",
            "--quite"
    }, description = "Reduces log output (ERROR for FA³ST packages, ERROR for all other packages). Default information about the starting process will still be printed.")
    public boolean quite = false;

    @Option(names = {
            "-v",
            "--verbose"
    }, description = "Enables verbose logging (INFO for FA³ST packages, WARN for all other packages).")
    public boolean verbose = false;

    @Option(names = "-vv", description = "Enables very verbose logging (DEBUG for FA³ST packages, INFO for all other packages).")
    public boolean veryVerbose = false;

    @Option(names = "-vvv", description = "Enables very very verbose logging (TRACE for FA³ST packages, DEBUG for all other packages).")
    public boolean veryVeryVerbose = false;

    /**
     * Entry point of the application.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        new CommandLine(new App()).execute(args);

        new SpringApplicationBuilder(App.class)
                .bannerMode(Mode.CONSOLE)
                .banner(App::printBanner)
                .run(args);
    }


    @Override
    public void run() {
        configureLogging();
    }


    private static void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
        out.println("            _____                                                       ");
        out.println("           |___ /                                                       ");
        out.println(" ______      |_ \\   _____ _______     _____            _     _              ");
        out.println("|  ____/\\   ___) | / ____|__   __|   |  __ \\          (_)   | |             ");
        out.println("| |__ /  \\ |____/ | (___    | |      | |__) |___  __ _ _ ___| |_ _ __ _   _ ");
        out.println("|  __/ /\\ \\        \\___ \\   | |      |  _  // _ \\/ _` | / __| __| '__| | | |");
        out.println("| | / ____ \\       ____) |  | |      | | \\ \\  __/ (_| | \\__ \\ |_| |  | |_| |");
        out.println("|_|/_/    \\_\\     |_____/   |_|      |_|  \\_\\___|\\__, |_|___/\\__|_|   \\__, |");
        out.println("                                                  __/ |                __/ |");
        out.println("                                                 |___/                |___/ ");
        out.println("----------------------------------------------------------------------------");
        out.println();
        out.println("FA³ST Registry is now running...");
    }


    private void configureLogging() {
        if (veryVeryVerbose) {
            Fa3stFilter.setLevelFa3st(Level.TRACE);
            Fa3stFilter.setLevelExternal(Level.DEBUG);
        }
        else if (veryVerbose) {
            Fa3stFilter.setLevelFa3st(Level.DEBUG);
            Fa3stFilter.setLevelExternal(Level.INFO);
        }
        else if (verbose) {
            Fa3stFilter.setLevelFa3st(Level.INFO);
            Fa3stFilter.setLevelExternal(Level.WARN);
        }
        else if (quite) {
            Fa3stFilter.setLevelFa3st(Level.ERROR);
            Fa3stFilter.setLevelExternal(Level.ERROR);
        }

        LOGGER.info("Using log level for FA³ST packages: {}", Fa3stFilter.getLevelFa3st());
        LOGGER.info("Using log level for external packages: {}", Fa3stFilter.getLevelExternal());
    }
}
