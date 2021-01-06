package me.hoonti06.springrestdocs.api.controller.api.controller;

import me.hoonti06.springrestdocs.domain.Gender;
import me.hoonti06.springrestdocs.response.ApiResponseCode;
import me.hoonti06.springrestdocs.response.ApiResponseDto;
import me.hoonti06.springrestdocs.utils.EnumType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class EnumViewController {

    @GetMapping("/docs")
    public ApiResponseDto<Docs> findAll() {
        Map<String, String> apiResponseCodes = getDocs(ApiResponseCode.values());
        Map<String, String> gender = getDocs(Gender.values());

        return ApiResponseDto.createOK(
                Docs.testBuilder()
                        .apiResponseCodes(apiResponseCodes)
                        .genders(gender)
                        .build()
        );
    }

    private Map<String, String> getDocs(EnumType[] enumTypes) {
        return Arrays.stream(enumTypes)
                .collect(Collectors.toMap(EnumType::getId, EnumType::getText));
    }
}
