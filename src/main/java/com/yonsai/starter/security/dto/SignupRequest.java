package com.yonsai.starter.security.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 *
 * @param username
 * @param password
 */

public record SignupRequest(
        @NotBlank(message = "아이디는 필수입니다.")
        @Size(min = 4, max = 20, message = "아이디는 4~20자 입니다.")
        // Size는 임의로 넣어두었음
        String username,

        @NotBlank(message = "비밀번호는 필수입니다.")
        @Size(min = 8, max = 50, message = "비밀번호는 8자 이상입니다.")
        // Size는 임의로 넣어두었음
        String password
) {
}
