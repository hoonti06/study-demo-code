package me.hoonti06.springrestdocs.api.controller.api.controller;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class Docs {

    Map<String, String> apiResponseCodes;
    Map<String, String> genders;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    private Docs(Map<String, String> apiResponseCodes, Map<String, String> genders) {
        this.apiResponseCodes = apiResponseCodes;
        this.genders = genders;
    }
}
