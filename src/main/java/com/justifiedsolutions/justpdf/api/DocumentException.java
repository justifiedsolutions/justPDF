/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.api;

/**
 * An {@link Exception} thrown when some illegal action or state is triggered with a {@link
 * com.justifiedsolutions.justpdf.api.Document}.
 */
public class DocumentException extends Exception {

    /**
     * Creates a new {@code DocumentException}.
     */
    public DocumentException() {
        super();
    }

    /**
     * Creates a new {@code DocumentException}.
     *
     * @param message message
     */
    public DocumentException(String message) {
        super(message);
    }

    /**
     * Creates a new {@code DocumentException}.
     *
     * @param message message
     * @param cause   exception that caused this one to be thrown
     */
    public DocumentException(String message, Throwable cause) {
        super(message, cause);
    }
}
