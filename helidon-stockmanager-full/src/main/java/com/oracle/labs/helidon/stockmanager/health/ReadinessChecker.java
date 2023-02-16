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
package com.oracle.labs.helidon.stockmanager.health;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import com.oracle.labs.helidon.stockmanager.database.StockId;
import com.oracle.labs.helidon.stockmanager.database.StockLevel;
import com.oracle.labs.helidon.stockmanager.providers.DepartmentProvider;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * Readiness is different form Liveness. Readiness ensures that we actually are
 * actually ready to process transactions and are fully configured, liveliness
 * is more of a Hello World situation
 * 
 * @author tg13456
 *
 */
@ApplicationScoped
@Readiness
public class ReadinessChecker implements HealthCheck {
	@PersistenceContext(unitName = "stockmanagerJTA")
	private EntityManager entityManager;
	private DepartmentProvider departmentProvider;
	private String persistenceUnit;

	@Inject
	public ReadinessChecker(@ConfigProperty(name = "app.persistenceUnit") String persistenceUnitProvided,
			DepartmentProvider departmentProviderProvided) {
		this.persistenceUnit = persistenceUnitProvided;
		this.departmentProvider = departmentProviderProvided;
	}

	@Override
	public HealthCheckResponse call() {
		// there is no easy way for the entityManager to tell us if there is actually a
		// DB connection apart from trying to do something
		try {
			// if it returns without an exception it mean's we're good
			entityManager.find(StockLevel.class, new StockId("Bad Department", "Bad Item"));
		} catch (Exception e) {
			return HealthCheckResponse.named("stockmanager-ready").down()
					.withData("department", departmentProvider.getDepartment())
					.withData("persistanceUnit", persistenceUnit).withData("Exception", e.getClass().getName())
					.withData("Exception message", e.getMessage()).build();
		}
		return HealthCheckResponse.named("stockmanager-ready").up()
				.withData("department", departmentProvider.getDepartment()).withData("persistanceUnit", persistenceUnit)
				.build();
	}

}
