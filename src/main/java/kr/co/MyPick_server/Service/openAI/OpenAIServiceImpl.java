package kr.co.MyPick_server.Service.openAI;

import kr.co.MyPick_server.DTO.OpenAIReq;
import kr.co.MyPick_server.DTO.OpenAIRes;

public interface OpenAIServiceImpl {
    OpenAIReq getResponse(OpenAIRes openAIRes);
}