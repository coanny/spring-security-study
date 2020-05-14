package com.example.springsecuritystudy;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Map;

public class SlackTest {
    public static final String TOKEN = "xoxp-95224877543-210085453634-1108395473127-691785cd188cd23719ac5f66001b5b63";

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
        File file = new File("C:\\파일첨부 메뉴얼v1.1.pdf");
        HttpUtil httpUtil = new HttpUtil("https://slack.com/api/files.upload");
        String response = httpUtil.addParam("token", TOKEN)
                .addParam("channels", "C013T9Y5A0L")
                .addParam("text", "test")
                .addParam("file", file)
                .addParam("filename", file.getName())
                .submit();
        System.out.println(file.getName());
        System.out.println(response);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> resultMap = objectMapper.readValue(response, new TypeReference<Map>() {});

        Assertions.assertTrue((Boolean) resultMap.get("ok"));
    }
}
