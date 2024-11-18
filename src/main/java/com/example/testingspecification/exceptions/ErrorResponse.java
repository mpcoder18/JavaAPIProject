package com.example.testingspecification.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Used for returning uniform error messages in responses.
 * it helps with getting good error messages to the user
 */
@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String message;
}
