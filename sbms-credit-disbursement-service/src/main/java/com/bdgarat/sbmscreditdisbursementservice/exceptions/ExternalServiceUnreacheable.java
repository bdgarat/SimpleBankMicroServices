package com.bdgarat.sbmscreditdisbursementservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class ExternalServiceUnreacheable extends RuntimeException {
    public ExternalServiceUnreacheable(String msg) {
        super(msg);
    }
}

