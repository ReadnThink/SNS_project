package com.example.sns_project.infra.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomResponseUtil {
    public static void success(HttpServletResponse response, Object dto) {
        try {
            ObjectMapper om = new ObjectMapper();
            ResponseDto<?> responseDto = ResponseDto.success("로그인 성공", dto);
            String responseBody = om.writeValueAsString(responseDto);
            response.setCharacterEncoding(UTF_8.name());
            response.setContentType(APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.OK.value());
            response.getWriter().println(responseBody);
        } catch (Exception e) {
            log.error("서버 파싱 에러");
        }
    }
    public static void fail(HttpServletResponse response, String msg, HttpStatus httpStatus) {
        try {
            ObjectMapper om = new ObjectMapper();
            ResponseDto<?> responseDto = ResponseDto.error(msg, null);
            String responseBody = om.writeValueAsString(responseDto);
            response.setCharacterEncoding(UTF_8.name());
            response.setContentType(APPLICATION_JSON_VALUE);
            response.setStatus(httpStatus.value());
            response.getWriter().println(responseBody);
        } catch (Exception e) {
            log.error("서버 파싱 에러");
        }
    }

}
