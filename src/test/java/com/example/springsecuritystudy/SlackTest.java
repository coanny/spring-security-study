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
    public static final String TOKEN = "";

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
        File file = new File("F:\\theassetfund\\계정.txt");
        HttpUtil httpUtil = new HttpUtil("https://slack.com/api/files.upload");
        String response = httpUtil.addParam("token", TOKEN)
                .addParam("channels", "DSAUFCK4H")
                .addParam("file", file)
                .addParam("filename", file.getName())
                //.addParam("initial_comment", "initial_comment")
                .submit();
        System.out.println(file.getName());
        System.out.println(response);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> resultMap = objectMapper.readValue(response, new TypeReference<Map>() {});

        Assertions.assertTrue((Boolean) resultMap.get("ok"));
    }
}
