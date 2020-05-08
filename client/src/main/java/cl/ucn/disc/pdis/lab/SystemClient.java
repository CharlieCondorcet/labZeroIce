/*
 * Copyright (c) 2020 Charlie Condorcet engineer student.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cl.ucn.disc.pdis.lab;

import cl.ucn.disc.pdis.lab.zeroice.model.Engine;
import cl.ucn.disc.pdis.lab.zeroice.model.EnginePrx;
import com.zeroc.Ice.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

/**
 * Implementacion del cliente.
 *
 * @author Diego Urrutia-Astorga.
 */
public final class SystemClient {

    /**
     * The logger.
     */
    private static final Logger log = LoggerFactory.getLogger(SystemClient.class);

    /**
     * The main file
     *
     * @param args to use.
     */
    public static void main(final String[] args) {

        log.debug("Starting the Client ..");

        try (Communicator communicator = Util.initialize(getInitializationData(args))) {

            final ObjectPrx proxy = communicator.stringToProxy(Engine.class.getName() + ":default -p 10000 -z");
            final EnginePrx engine = EnginePrx.checkedCast(proxy);

            if (engine == null) {
                throw new IllegalStateException("Invalid Engine! (wrong proxy?)");
            }

            // Return the actual date.
            final String theDate = engine.getDate();
            log.debug("The Date: {}", theDate);

            // You must enter a Rut by keyboard.
            log.debug("\n\nWelcome User, enter a rut and I will return your DV!");

            Scanner scanner = new Scanner(System.in);
            String rut = scanner.nextLine();

            // Verify that the length of the Rut is not less than a valid one.
            while (rut.length() < 8) {
                log.debug("The Rut must be at least 8 characters long. Please try again.");
                rut = scanner.nextLine();
            }

            // The DV valid if the Rut  is valid.
            String theDV = engine.getDigitoVerificador(rut);
            log.debug("the DV is: [{}]", theDV);

        }

        log.debug("Done.");
    }

    /**
     * @param args to use as source.
     * @return the {@link InitializationData}.
     */
    private static InitializationData getInitializationData(String[] args) {

        // Properties
        final Properties properties = Util.createProperties(args);
        properties.setProperty("Ice.Package.model", "cl.ucn.disc.pdis.lab.zeroice");

        // https://doc.zeroc.com/ice/latest/property-reference/ice-trace
        // properties.setProperty("Ice.Trace.Admin.Properties", "1");
        // properties.setProperty("Ice.Trace.Locator", "2");
        // properties.setProperty("Ice.Trace.Network", "3");
        // properties.setProperty("Ice.Trace.Protocol", "1");
        // properties.setProperty("Ice.Trace.Slicing", "1");
        // properties.setProperty("Ice.Trace.ThreadPool", "1");
        // properties.setProperty("Ice.Compression.Level", "9");
        // properties.setProperty("Ice.Plugin.Slf4jLogger.java", "cl.ucn.disc.pdis.lab.zeroice.Slf4jLoggerPluginFactory");

        InitializationData initializationData = new InitializationData();
        initializationData.properties = properties;

        return initializationData;
    }

}
