package com.rookie.domain.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author yayee
 */
@Data
@AllArgsConstructor
public class TokenDTO {

    private String token;
    private CurrentLoginUserDTO currentUser;
}
