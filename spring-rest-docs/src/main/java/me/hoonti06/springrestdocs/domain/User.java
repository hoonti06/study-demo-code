package me.hoonti06.springrestdocs.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @NotNull
    @Column(nullable = false)
    private LocalDate birthDate;

    private String hobby;

    @Builder
    private User(String name, Gender gender, LocalDate birthDate, String hobby) {
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        this.hobby = hobby;
    }

    public void update(Gender gender, String hobby) {
        this.gender = gender;
        this.hobby = hobby;
    }


}
