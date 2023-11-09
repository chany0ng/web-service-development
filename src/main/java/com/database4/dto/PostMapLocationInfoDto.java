package com.database4.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class PostMapLocationInfoDto {
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String user_id;
}
