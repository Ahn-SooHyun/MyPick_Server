package kr.co.MyPick_server.DTO.eMail;

import lombok.Data;

@Data
public class MailSendReq {
    private int idx;
    private String id;
    private String code;
}
