package com.desss.zcms.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class UspsConfig {

    public static final String CLIENT_ID = "PhxLySp0qmxoaTQ6dQCtY2535r6Es7eWnVkO9ToAAsziLmBe";
    public static final String CLIENT_SECRET = "aZOQWxuE4IdTnRYjnyysdGaLNFzYYAHIJtuHfzwiYJaM5Q1m3qwTC787ufs0kuxV";

    public static final String TOKEN_URL = "https://api.usps.com/oauth2/v3/token";
    public static final String RATE_URL = "https://api.usps.com/prices/v3/base-rates/search";
}
