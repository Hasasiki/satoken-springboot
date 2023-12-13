package com.ha.satoken.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class WeChatUtil {
    //    @Value("${wechat.appid}")
    private final static String APPID = "";  //your appid
    //    @Value("${wechat.secret}")
    private final static String SECRET  = ""; //your secret

    public static String getOpenId(String code) {
        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session?appid="
                + APPID + "&secret=" + SECRET + "&js_code=" + code + "&grant_type=authorization_code";

        try {
            URL url = new URL(requestUrl);
            JsonNode json = getWxResponseJson(url);
            return json.get("openid").asText();
        } catch (Exception e) {
            log.error("WX openid request  error~!: {}", e.getMessage());
            return "invalid";
        }
    }

    private static JsonNode getWxResponseJson(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();
        while ((line = br.readLine()) != null) {
            response.append(line);
        }
        br.close();

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(response.toString());
    }
}
