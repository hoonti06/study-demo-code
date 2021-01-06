package me.hoonti06.springrestdocs.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.hoonti06.springrestdocs.utils.EnumType;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum Gender implements EnumType {
    MALE("남자"),
    FEMALE("여자");

    private final String text;

    @Override
    public String getId() {
        return this.name();
    }
}
