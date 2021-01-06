package me.hoonti06.springrestdocs.api.controller.document;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.hoonti06.springrestdocs.ApiDocumentationTest;
import me.hoonti06.springrestdocs.domain.Gender;
import me.hoonti06.springrestdocs.service.dto.UserDto;
import me.hoonti06.springrestdocs.service.dto.UserUpdateDto;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserUpdateV1DocumentationTests extends ApiDocumentationTest {

    @Test
    public void update() throws Exception {
        //given(준비)

        given(userService.update(eq(1L), any(UserUpdateDto.class)))
                .willReturn(UserDto.builder()
                        .id(1L)
                        .name("John Doe")
                        .gender(Gender.MALE)
                        .birthDate(LocalDate.of(2020, 2, 2))
                        .hobby("game")
                        .build());

        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.gender = Gender.FEMALE;
        updateRequest.hobby = "soccer";

        //when(실행)
        ResultActions result = this.mockMvc.perform(
                put("/v1/users/{id}", 1L)
                    .content(objectMapper.writeValueAsString(updateRequest))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
        );

        //then(검증)
        result.andExpect(status().isOk())
                .andDo(document("users-update-v1",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("아이디")
                        ),
                        requestFields(
                                fieldWithPath("gender").type(JsonFieldType.STRING).description(generateLinkCode(GENDER)),
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
    public static class UpdateRequest {
        Gender gender;
        String hobby;
    }
}
