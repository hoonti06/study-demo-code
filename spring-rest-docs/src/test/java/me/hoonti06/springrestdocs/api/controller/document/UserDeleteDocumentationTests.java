package me.hoonti06.springrestdocs.api.controller.document;

import me.hoonti06.springrestdocs.ApiDocumentationTest;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static me.hoonti06.springrestdocs.api.controller.document.utils.ApiDocumentUtils.getDocumentRequest;
import static me.hoonti06.springrestdocs.api.controller.document.utils.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserDeleteDocumentationTests extends ApiDocumentationTest {

    @Test
    public void delete_by_id() throws Exception {
        //given(준비)
        doNothing().when(userService)
                .delete(1L);

        //when(실행)
        ResultActions result = this.mockMvc.perform(
                delete("/v1/users/{id}", 1L)
                    .accept(MediaType.APPLICATION_JSON)
        );

        //then(검증)
        result.andExpect(status().isOk())
                .andDo(document("users-delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("아이디")
                        )
                ));
    }
}
