package me.hoonti06.springrestdocs.api.controller.document;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.hoonti06.springrestdocs.ApiDocumentationTest;
import me.hoonti06.springrestdocs.domain.Gender;
import me.hoonti06.springrestdocs.service.dto.UserCreateDto;
import me.hoonti06.springrestdocs.service.dto.UserDto;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static me.hoonti06.springrestdocs.api.controller.document.utils.ApiDocumentUtils.getDocumentRequest;
import static me.hoonti06.springrestdocs.api.controller.document.utils.ApiDocumentUtils.getDocumentResponse;
import static me.hoonti06.springrestdocs.api.controller.document.utils.DocumentFormatGenerator.getDateFormat;
import static me.hoonti06.springrestdocs.api.controller.document.utils.DocumentLinkGenerator.DocUrl.GENDER;
import static me.hoonti06.springrestdocs.api.controller.document.utils.DocumentLinkGenerator.generateLinkCode;
import static me.hoonti06.springrestdocs.api.controller.document.utils.DocumentLinkGenerator.generateText;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserCreateV1DocumentationTests extends ApiDocumentationTest {

    @Test
    public void signUp() throws Exception {
        //given(준비)
        given(userService.signUp(any(UserCreateDto.class)))
                .willReturn(UserDto.builder()
                        .id(1L)
                        .name("John Doe")
                        .gender(Gender.MALE)
                        .birthDate(LocalDate.of(2020, 2, 2))
                        .hobby("game")
                        .build()
                );

        Request request = new Request();
        request.name = "John Doe";
        request.gender = "MALE";
        request.birthDate = "2020-02-02";
        request.hobby = "game";

        //when(실행)
        ResultActions result = this.mockMvc.perform(
                post("/v1/users")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );


        //then(검증)
        result.andExpect(status().isOk())
                .andDo(document("users-signup-v1",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("gender").type(JsonFieldType.STRING).description(generateLinkCode(GENDER)),
                                fieldWithPath("birthDate").type(JsonFieldType.STRING).attributes(getDateFormat()).description("생년월일"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("취미").optional()
                        ),
                        responseFields(beneathPath("data").withSubsectionId("data"),
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("아이디"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("gender").type(JsonFieldType.STRING).description(generateLinkCode(GENDER)),
                                fieldWithPath("genderName").type(JsonFieldType.STRING).description(generateText(GENDER)),
                                fieldWithPath("birthDate").type(JsonFieldType.STRING)
                                        .attributes(getDateFormat()).description("생년월일"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("취미")
                        )
                ));
    }
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Request {
        String name;
        String gender;
        String birthDate;
        String hobby;
    }
}
