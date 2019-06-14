package com.hamster.chat.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MsgInfo implements Serializable {
    private String account;
    private String content;
    private Date time;
    private String roomId;

}
