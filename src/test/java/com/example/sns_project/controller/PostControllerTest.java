package com.example.sns_project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @Test
    @DisplayName("/posts 요청성공")
    void test1() throws Exception {
        PostCreate postCreate = new PostCreate("제목", "내용");

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(postCreate))
                )
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("글작성에 성공했습니다."))
                .andExpect(status().isOk())
                .andDo(print())
        ;
    }
    @Test
    @DisplayName("/posts 요청시 title 필수")
    void test2() throws Exception {
        PostCreate postCreate = new PostCreate("", "123");

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(postCreate))
                )
                .andExpect(jsonPath("$.code").value(-1))
                .andExpect(jsonPath("$.message").value("유효성검사 실패"))
                .andExpect(jsonPath("$.data.title").value("타이틀을 입력해주세요"))
                .andExpect(status().isBadRequest())
                .andDo(print())
        ;
    }

    @Test
    @DisplayName("/posts 요청시 글의 제목은 30자 이내여야 함")
    void test3() throws Exception {
        PostCreate postCreate = new PostCreate("내용은 30글자 이내로 입력 가능합니다.내용은 30글자 이내로 입력 가능합니다.내용은 30글자 이내로 입력 가능합니다.내용은 30글자 이내로 입력 가능합니다.", "123");

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(postCreate))
                )
                .andExpect(jsonPath("$.code").value(-1))
                .andExpect(jsonPath("$.message").value("유효성검사 실패"))
                .andExpect(jsonPath("$.data.title").value("내용은 30글자 이내로 입력 가능합니다."))
                .andExpect(status().isBadRequest())
                .andDo(print())
        ;
    }
}