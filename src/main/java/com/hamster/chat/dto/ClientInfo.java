package com.hamster.chat.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ClientInfo {
    private String clientId;
    private boolean isOnline;
    private long mostSignificantBits;
    private long leastSignificantBits;
    private Date lastConnectedTime;
}
