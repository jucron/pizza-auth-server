package com.renault.pizzaauthserver.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenProperties {

    private String SECRET_KEY;
    private int tokenExpirationTime;

}
