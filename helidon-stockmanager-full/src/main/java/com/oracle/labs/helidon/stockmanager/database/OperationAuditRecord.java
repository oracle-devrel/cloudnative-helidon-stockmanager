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

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Entity
@Table(name = "OperationAuditRecord", indexes = { @Index(columnList = "departmentName, operationTs", unique = false) })
@NoArgsConstructor
public class OperationAuditRecord {
	@Id
	@GeneratedValue
	private long operationId;
	@Column(name = "operationTs", nullable = false)
	private Timestamp operationTs = new Timestamp(System.currentTimeMillis());
	@Column(name = "succeded", nullable = false)
	private Boolean succeded;
	@Column(name = "errorMessage", nullable = true)
	private String errorMessage;
	@Column(name = "operationType", nullable = false)
	private OperationAuditType operationType;
	@Column(name = "operationUser", nullable = false)
	private String operationUser;
	@Column(name = "departmentName", nullable = false)
	private String departmentName;
	@Column(name = "itemName", nullable = false)
	private String itemName;
	@Column(name = "itemCount", nullable = true)
	private Integer itemCount;

	private OperationAuditRecord(@NonNull OperationAuditType operationType, @NonNull Boolean succeded,
			String errorMessage, @NonNull String operationUser, @NonNull String departmentName,
			@NonNull String itemName, Integer itemCount) {
		super();
		this.operationType = operationType;
		this.succeded = succeded;
		this.errorMessage = errorMessage;
		this.operationUser = operationUser;
		this.departmentName = departmentName;
		this.itemName = itemName;
		this.itemCount = itemCount;
	}

	public static OperationAuditRecord create(@NonNull Boolean succeded, String errorMessage,
			@NonNull String operationUser, @NonNull String departmentName, @NonNull String itemName,
			@NonNull Integer itemCount) {
		return new OperationAuditRecord(OperationAuditType.CREATE, succeded, errorMessage, operationUser,
				departmentName, itemName, itemCount);
	}

	public static OperationAuditRecord update(@NonNull Boolean succeded, String errorMessage,
			@NonNull String operationUser, @NonNull String departmentName, @NonNull String itemName,
			@NonNull Integer itemCount) {
		return new OperationAuditRecord(OperationAuditType.UPDATE, succeded, errorMessage, operationUser,
				departmentName, itemName, itemCount);
	}

	public static OperationAuditRecord delete(@NonNull Boolean succeded, String errorMessage,
			@NonNull String operationUser, @NonNull String departmentName, @NonNull String itemName) {
		return new OperationAuditRecord(OperationAuditType.DELETE, succeded, errorMessage, operationUser,
				departmentName, itemName, null);
	}
}
