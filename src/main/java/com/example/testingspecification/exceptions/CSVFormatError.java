package com.example.testingspecification.exceptions;

/**
 * A custom error for csv format exceptions.
 * The purpose of this class is to allow the application to throw a specific,
 * meaningful error when it encounters an issue related to CSV format, rather
 * than using a generic exception. This helps improve the clarity and maintainability of
 * the code by explicitly indicating what kind of error occurred.
 */
public class CSVFormatError extends RuntimeException {
    public CSVFormatError(String message) {
        super(message);
    }
}
