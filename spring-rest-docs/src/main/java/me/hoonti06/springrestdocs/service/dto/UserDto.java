package me.hoonti06.springrestdocs.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.hoonti06.springrestdocs.domain.Gender;
import me.hoonti06.springrestdocs.domain.User;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private Gender gender;
    private String genderName;
    private LocalDate birthDate;
    private String hobby;

    @Builder
    private UserDto(Long id, String name, Gender gender, LocalDate birthDate, String hobby) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.genderName = gender.getText();
        this.birthDate = birthDate;
        this.hobby = hobby;
    }

    public static UserDto of(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .gender(user.getGender())
                .birthDate(user.getBirthDate())
                .hobby(user.getHobby())
                .build();
    }
}
