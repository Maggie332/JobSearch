package com.laioffer.job.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRequestBody {
    @JsonProperty("user_id")
    public String userId;

    @JsonProperty("password")
    public String password;
}
