package com.yonsai.starter.security.dto;

public record LoginRequest(
        String username,
        String password
) {
}
