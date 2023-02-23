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
package com.oracle.labs.helidon.stockmanager.database;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * These annotations are processed by Lombok, you cn of course create your own
 * getters. setters, all and no args constructors, but Lombok makes it easier.
 * You do however need to include Lombok in your pom.xml AND ALSO install it in
 * the Eclipse development environment (it runs in the background processing
 * annotations as part of the Eclipse continuous background compilation process)
 * 
 * To Run it in the background just run the Lombok jar file which will try and
 * locate the appropriate IDE's for you
 */
// Creates setters, getters, equals, hashcode and toString
@Data
// setup the constructors for us
@NoArgsConstructor
@AllArgsConstructor
// Tell JPA This is something that we want to save, hibernate will do a bunch of stuff for 
// us on this basis, including creating the required tables (assuming we've setup it's 
// properties file correctly
@Entity
@Table(name = "StockLevel")
public class StockLevel {
	@EmbeddedId
	private StockId stockId;

	@Column(name = "itemCount")
	private int itemCount;
}
