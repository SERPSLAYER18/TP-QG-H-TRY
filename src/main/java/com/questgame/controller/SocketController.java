package com.questgame.controller;

import com.google.gson.Gson;
import com.questgame.dto.*;
import com.questgame.service.impl.QuestionServiceImpl;
import com.questgame.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Map;


@Controller
@RequiredArgsConstructor
@Slf4j
public class SocketController {

    private final QuestionServiceImpl questionService;
    private final UserServiceImpl userService;


    @MessageMapping("/serverMessagePool")
    @SendTo("/topic/clientMessagePool")
    public Object replyMessage(String  JSONString) {
    Gson gson = new Gson();
    Map map = gson.fromJson(JSONString, Map.class);
    MessageType messageType = MessageType.valueOf ((String)  map.get("messageType"));
    switch (messageType){

        case question:
            QuestionQueryDto questionDtoQuery = gson.fromJson(JSONString, QuestionQueryDto.class);
            return questionService.getRandomQuestion(
                    questionDtoQuery.getTopic(),
                    questionDtoQuery.getDifficulty());

        case confirmation:
            String userName = (String) map.get("userName");
            String password = ((String) map.get("password"));
            boolean passwordIsValid = (userService.get(userName,password)!=null);
            return new ConformationDto(passwordIsValid);

        case correctAnswer:
            int id = Math.toIntExact(Math.round((double) map.get("id")));
            return new CorrectAnswerDto(questionService.getCorrectAnswer(id));
        default:
            return null;
    }


    }

}