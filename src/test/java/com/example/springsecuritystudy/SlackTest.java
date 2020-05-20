package com.example.springsecuritystudy;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Map;

public class SlackTest {
    /**
     * https://api.slack.com/tutorials/slack-apps-and-postman
     */
    public static final String TOKEN = "xoxp-95224877543-210085453634-1147169905824-4f583920f429a5fd3c8dd61083888fd5";

    /**
     *
     * https://api.slack.com/methods/chat.postMessage
     *
     * @throws Exception
     */
    @Test
    public void 메시지보내기() throws Exception {
        HttpUtil httpUtil = new HttpUtil("https://slack.com/api/chat.postMessage");
        String response = httpUtil.addParam("token", TOKEN)
                .addParam("channel", "C013T9Y5A0L")
                .addParam("text", "test")
                .submit();

        System.out.println(response);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> resultMap = objectMapper.readValue(response, new TypeReference<Map>() {});

        Assertions.assertTrue((Boolean) resultMap.get("ok"));

    }

    /**
     * https://api.slack.com/methods/files.upload
     */
    @Test
    public void 파일업로드() throws Exception {
        File file = new File("F:\\발표자료\\2020년워크샾.pptx");
        HttpUtil httpUtil = new HttpUtil("https://slack.com/api/files.upload");
        String response = httpUtil.addParam("token", TOKEN)
                .addParam("channels", "D65FK4801")
                .addParam("file", file)
                .addParam("filename", file.getName())
                .addParam("initial_comment", "initial_comment")
                .submit();
        System.out.println(file.getName());
        System.out.println(response);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> resultMap = objectMapper.readValue(response, new TypeReference<Map>() {});

        Assertions.assertTrue((Boolean) resultMap.get("ok"));
    }
}
