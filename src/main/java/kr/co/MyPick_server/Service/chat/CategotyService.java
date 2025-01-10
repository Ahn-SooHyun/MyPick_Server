package kr.co.MyPick_server.Service.chat;

import kr.co.MyPick_server.DAO.Chat.CategoryDAO;
import kr.co.MyPick_server.DTO.chat.CategoryChangeReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategotyService implements CategotyServiceImpl{

    @Autowired
    private CategoryDAO categoryDAO;

    @Override
    public int ChatCreateRoom(int IDX, String prompt) {

        // "/" 뒤 실제 명령어 부분 추출
        // 모든 공백을 제거한 문자열 (슬래시 명령어 여부 판단용)
        String noSpacePrompt = prompt.replaceAll("\\s+", "");
        String command = noSpacePrompt.substring(1);
        // 예: " / 음 악 " -> 공백 제거 -> "/음악" -> command = "음악"

        // CategoryChangeReq 생성
        CategoryChangeReq categoryChangeReq = new CategoryChangeReq();
        categoryChangeReq.setIDX(IDX);      // JWT에서 추출한 사용자 인덱스
        categoryChangeReq.setCategory(command);

        int result = 0;

        switch (categoryChangeReq.getCategory()) {
            case "음악":
            case "Music":
            case "music":
                result = 1;
                categoryChangeReq.setCategory("음악");
                break;
            case "영화":
            case "Movie":
            case "movie":
                categoryChangeReq.setCategory("영화");
                result = 2;
                break;
            case "도서":
            case "Book":
            case "book":
                categoryChangeReq.setCategory("도서");
                result = 3;
                break;
            case "게임":
            case "Game":
            case "game":
                categoryChangeReq.setCategory("게임");
                result = 4;
                break;
            default:
                return 0;
        }

        categoryDAO.categoryChange(categoryChangeReq);

        return result;
    }
}
