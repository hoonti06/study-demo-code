package me.hoonti06.springrestdocs.api.controller.document;

import me.hoonti06.springrestdocs.ApiDocumentationTest;
import me.hoonti06.springrestdocs.domain.Gender;
import me.hoonti06.springrestdocs.service.dto.UserDto;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static me.hoonti06.springrestdocs.api.controller.document.utils.ApiDocumentUtils.getDocumentRequest;
import static me.hoonti06.springrestdocs.api.controller.document.utils.ApiDocumentUtils.getDocumentResponse;
import static me.hoonti06.springrestdocs.api.controller.document.utils.DocumentFormatGenerator.getDateFormat;
import static me.hoonti06.springrestdocs.api.controller.document.utils.DocumentLinkGenerator.DocUrl.GENDER;
import static me.hoonti06.springrestdocs.api.controller.document.utils.DocumentLinkGenerator.generateLinkCode;
import static me.hoonti06.springrestdocs.api.controller.document.utils.DocumentLinkGenerator.generateText;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserReadDocumentationTest extends ApiDocumentationTest {

    @Test
    public void findById() throws Exception {
        //given(준비)
        given(userService.findById(1L))
                .willReturn(UserDto.builder()
                        .id(1L)
                        .name("John Doe")
                        .gender(Gender.MALE)
                        .birthDate(LocalDate.of(2020, 2, 2))
                        .hobby("game")
                        .build()
                );

        //when(실행)
        ResultActions result = this.mockMvc.perform(
                get("/v1/users/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON)
        );

        //then(검증)
        result.andExpect(status().isOk())
                .andDo(document("users-find",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("아이디")
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

    @Test
    public void findAll() throws Exception {

        //given
        List<UserDto> responseList = Arrays.asList(
                UserDto.builder()
                        .id(1L)
                        .name("John Doe")
                        .gender(Gender.MALE)
                        .birthDate(LocalDate.of(2020, 2, 2))
                        .hobby("game")
                        .build(),
                UserDto.builder()
                        .id(2L)
                        .name("Jane Doe")
                        .gender(Gender.FEMALE)
                        .birthDate(LocalDate.of(2000, 1, 1))
                        .hobby("game")
                        .build()
        );

        given(userService.findAll())
                .willReturn(responseList);

        //when
        ResultActions result = this.mockMvc.perform(
                get("/v1/users")
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(status().isOk())
                .andDo(document("users-find-all",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(beneathPath("data[]").withSubsectionId("data"),
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

}
