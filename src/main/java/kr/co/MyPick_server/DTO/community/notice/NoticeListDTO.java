package kr.co.MyPick_server.DTO.community.notice;

import lombok.Data;

import java.util.Date;

@Data
public class NoticeListDTO {
    private int idx;
    private String title;
    private String category;
    private String content;
    private String editor;
    private Date editorDate;
}
