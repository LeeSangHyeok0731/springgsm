package com.example.springgsm.controller;

import com.example.springgsm.entity.Student;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloController {

    private final ChatClient chatClient;

    public HelloController(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

    private String systemRule = """
        당신은 HR 도우미 입니다. 
        학생 목록에 근거하여 답변하세요.
        목록에 없으면 "목록에 없습니다"라고 답하세요.
        JSON 형식으로 응답하세요.
    """;

    @GetMapping("/chat")
    public List<Student> chat(String question){
        // System message(규칙, 역할), User message(사용자): Prompt
        String students = """
            이름: 김준혁 | 학교: 광주소프트웨어마이스터고 | 동아리: 루미큐브 | 이메일: s24020@gsm.hs.kr
            이름: 이상혁 | 학교: 광주소프트웨어마이스터고 | 동아리: 더모먼트 | 이메일: s24060@gsm.hs.kr
        """;

        String userContent = """
            [학생목록 시작]
            %s
            [학생목록 끝]
            질문: %s
        """.formatted(students, question);

        return chatClient.prompt()
                .system(systemRule)
                .user(userContent)
                .call()
                //.content()
                .entity(new ParameterizedTypeReference<List<Student>>() {});
    }
}