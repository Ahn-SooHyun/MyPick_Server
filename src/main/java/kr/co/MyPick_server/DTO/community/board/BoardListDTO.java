package kr.co.MyPick_server.DTO.community.board;

import lombok.Data;

import java.util.Date;

@Data
public class BoardListDTO {
    private int idx;
    private String title;
    private String category;
    private String content;
    private String editor;
    private Date editorDate;
}
