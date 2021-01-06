package me.hoonti06.springrestdocs.api.controller.dto;

import lombok.Getter;
import me.hoonti06.springrestdocs.domain.Gender;
import me.hoonti06.springrestdocs.service.dto.UserCreateDto;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
public class UserCreateV1 {

    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @NotBlank(message = "성별은 필수입니다.")
    private Gender gender;

    @NotNull(message = "생일은 필수입니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private String hobby;

    public UserCreateDto toDto() {
        return UserCreateDto.builder()
                .name(name)
                .gender(gender)
                .birthDate(birthDate)
                .hobby(hobby)
                .build();
    }
}
