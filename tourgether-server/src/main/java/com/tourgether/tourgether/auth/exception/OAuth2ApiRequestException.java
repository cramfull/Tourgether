package com.tourgether.tourgether.auth.exception;

import com.tourgether.tourgether.common.exception.BadGatewayException;

public class OAuth2ApiRequestException extends BadGatewayException {
    public OAuth2ApiRequestException(String message) {
        super(message);
    }
}
