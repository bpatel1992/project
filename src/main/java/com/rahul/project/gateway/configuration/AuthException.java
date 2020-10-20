package com.rahul.project.gateway.configuration;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AuthException implements Serializable {

    private String errorDescription;
}