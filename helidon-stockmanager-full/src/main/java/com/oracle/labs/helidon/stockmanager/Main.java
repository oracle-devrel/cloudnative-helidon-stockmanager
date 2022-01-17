/*Copyright (c) 2021 Oracle and/or its affiliates.

The Universal Permissive License (UPL), Version 1.0

Subject to the condition set forth below, permission is hereby granted to any
person obtaining a copy of this software, associated documentation and/or data
(collectively the "Software"), free of charge and under any and all copyright
rights in the Software, and any and all patent rights owned or freely
licensable by each licensor hereunder covering either (i) the unmodified
Software as contributed to or provided by such licensor, or (ii) the Larger
Works (as defined below), to deal in both

(a) the Software, and
(b) any piece of software and/or hardware listed in the lrgrwrks.txt file if
one is included with the Software (each a "Larger Work" to which the Software
is contributed by such licensors),

without restriction, including without limitation the rights to copy, create
derivative works of, display, perform, and distribute the Software and make,
use, sell, offer for sale, import, export, have made, and have sold the
Software and the Larger Work(s), and to sublicense the foregoing rights on
either these or other terms.

This license is subject to the following condition:
The above copyright notice and either this complete permission notice or at
a minimum a reference to the UPL must be included in all copies or
substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */

package com.oracle.labs.helidon.stockmanager;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import io.helidon.config.Config;
import io.helidon.config.ConfigSources;
import io.helidon.config.PollingStrategies;
import io.helidon.config.spi.ConfigSource;
import io.helidon.microprofile.server.Server;
import lombok.extern.slf4j.Slf4j;

/**
 * Main method simulating trigger of main method of the server.
 */

@Slf4j
public final class Main {

	/**
	 * Cannot be instantiated.
	 */
	private Main() {
	}

	/**
	 * Application main entry point.
	 * 
	 * @param args command line arguments
	 * @throws IOException if there are problems reading logging properties
	 */
	public static void main(final String[] args) throws IOException {
		// Helidon will automatically locate a logging.propoerties if one exists in the
		// classpath or current working directory and will use that to configure the
		// logging for us, so we don't need to explicitly configure logging

		Server server = Server.builder().config(buildConfig()).build().start();

		log.info("http://localhost:" + server.port());
	}

	/**
	 * 
	 * @return
	 */
	private static Config buildConfig() {

		List<Supplier<? extends ConfigSource>> configSourcesToScan = new ArrayList<>(5);
		configSourcesToScan.add(ConfigSources.file("conf/stockmanager-config.yaml")
				.pollingStrategy(PollingStrategies.regular(Duration.ofSeconds(5))).optional().build());
		configSourcesToScan.add(ConfigSources.file("conf/stockmanager-network.yaml").optional().build());
		// We actually use the env or system properties to get these, but let's leave
		// this here as it'll let people easily switch to using a config file instead of
		// them, it's optional so no harm if it's not there
		configSourcesToScan.add(ConfigSources.file("confsecure/stockmanager-database.yaml").optional().build());
		configSourcesToScan.add(ConfigSources.file("confsecure/stockmanager-security.yaml").build());
		configSourcesToScan.add(ConfigSources.classpath("META-INF/microprofile-config.properties").build());
		return Config.builder().sources(configSourcesToScan).build();
	}
}
