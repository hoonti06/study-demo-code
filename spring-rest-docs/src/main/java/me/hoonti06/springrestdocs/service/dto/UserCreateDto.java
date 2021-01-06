package me.hoonti06.springrestdocs.service.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import me.hoonti06.springrestdocs.domain.Gender;
import me.hoonti06.springrestdocs.domain.User;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserCreateDto {
    private String name;
    private Gender gender;
    private LocalDate birthDate;
    private String hobby;

    @Builder
    private UserCreateDto(String name, Gender gender, LocalDate birthDate, String hobby) {
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        this.hobby = hobby;
    }

    public User toEntity() {
        return User.builder()
                .name(name)
                .gender(gender)
                .birthDate(birthDate)
                .hobby(hobby)
                .build();
    }
}
