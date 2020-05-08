/*
 * Copyright (c) 2020 Diego Urrutia-Astorga. http://durrutia.cl.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which accompanies
 * this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package cl.ucn.disc.pdis.lab;

import cl.ucn.disc.pdis.lab.services.EngineImpl;
import cl.ucn.disc.pdis.lab.zeroice.model.Engine;
import com.zeroc.Ice.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementacion del server de articulos.
 *
 * @author Diego Urrutia-Astorga.
 */
public final class SystemServer {

    /**
     * The logger.
     */
    private static final Logger log = LoggerFactory.getLogger(SystemServer.class);

    /**
     * @param args to use.
     */
    public static void main(final String[] args) {

        log.debug("Starting the Server ..");

        // The communicator
        try (Communicator communicator = Util.initialize(getInitializationData(args))) {

            // The adapter
            final ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("EngineAdapter", "default -p 10000 -z");

            // The interface + implementation
            final Engine engine = new EngineImpl();

            // The unique identity
            final Identity identity = Util.stringToIdentity(Engine.class.getName());
            log.debug("Using name <{}> and category <{}> as identity.", identity.name, identity.category);

            // Register the API in the framework
            adapter.add(engine, identity);
            adapter.activate();

            log.debug("Waiting for connections ..");

            // .. waiting.
            communicator.waitForShutdown();
        }

        log.debug("Server ended!");

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
