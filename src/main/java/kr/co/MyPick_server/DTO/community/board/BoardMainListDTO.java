package kr.co.MyPick_server.DTO.community.board;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class BoardMainListDTO {
    private int idx;
    private String title;
    @JsonFormat(pattern = "yyyy-MM-dd") // 날짜 형식을 "년-월-일" 형식으로 지정
    private Timestamp editorDate;
}
