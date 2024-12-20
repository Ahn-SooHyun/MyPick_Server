package kr.co.MyPick_server.controller.openAI;

import kr.co.MyPick_server.DTO.OpenAIReq;
import kr.co.MyPick_server.DTO.OpenAIRes;
import kr.co.MyPick_server.Service.openAI.OpenAIServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/openai")
public class OpenAIController {

    @Autowired
    private kr.co.MyPick_server.Service.openAI.OpenAIServiceImpl openAIService;

    @GetMapping("/ask")
    public OpenAIReq askOpenAI(@ModelAttribute OpenAIRes openAIRes) {
        return openAIService.getResponse(openAIRes);
    }
}