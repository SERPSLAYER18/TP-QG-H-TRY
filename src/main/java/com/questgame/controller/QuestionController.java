package com.questgame.controller;

import com.google.gson.Gson;
import com.questgame.dao.impl.TopicDaoImpl;
import com.questgame.dto.DifficultyDto;
import com.questgame.dto.QuestionDto;
import com.questgame.dto.QuestionQueryDto;
import com.questgame.dto.TopicDto;
import com.questgame.service.TopicService;
import com.questgame.service.impl.DifficultyServiceImpl;
import com.questgame.service.impl.QuestionServiceImpl;
import com.questgame.service.impl.TopicServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class QuestionController {

    private static final int QUESTONS_IN_EACH_TOPIC = 5;
    private final QuestionServiceImpl questionService;

    @MessageMapping("/single_question")
    @SendTo("/topic/single_question")
    public QuestionDto randomQuestionByJSONString(String JSONString) {
        Gson gson = new Gson();
        QuestionQueryDto questionDtoQuery = gson.fromJson(JSONString, QuestionQueryDto.class);
        return questionService.getRandomQuestion(
                questionDtoQuery.getTopic(),
                questionDtoQuery.getDifficulty());
    }

    @MessageMapping("/miltiple_questions")
    @SendTo("/topic/miltiple_questions")
    public QuestionDto[][] randomQuestions(String sTopicCount) {
        int topicsCount = Integer.parseInt(sTopicCount);
        QuestionDto[][] questionDtos = new QuestionDto[topicsCount][QUESTONS_IN_EACH_TOPIC];

        //TO DO:
        //FILL questionDtos

        return questionDtos;
    }

}