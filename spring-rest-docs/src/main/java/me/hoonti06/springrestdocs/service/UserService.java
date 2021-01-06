package me.hoonti06.springrestdocs.service;

import lombok.RequiredArgsConstructor;
import me.hoonti06.springrestdocs.domain.Gender;
import me.hoonti06.springrestdocs.domain.User;
import me.hoonti06.springrestdocs.domain.UserRepository;
import me.hoonti06.springrestdocs.exception.NotFoundException;
import me.hoonti06.springrestdocs.service.dto.UserCreateDto;
import me.hoonti06.springrestdocs.service.dto.UserDto;
import me.hoonti06.springrestdocs.service.dto.UserUpdateDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(UserDto::of)
                .collect(Collectors.toList());
    }

    public List<UserDto> findByGender(Gender gender) {
        return userRepository.findByGender(gender).stream()
                .map(UserDto::of)
                .collect(Collectors.toList());
    }

    public UserDto findById(Long id) {
        return userRepository.findById(id)
                .map(UserDto::of)
                .orElseThrow(NotFoundException::new);
    }

    public UserDto signUp(UserCreateDto create) {
        User target = create.toEntity();
        User created = userRepository.save(target);

        return UserDto.of(created);
    }

    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        userRepository.delete(user);
    }

    @Transactional
    public UserDto update(Long id, UserUpdateDto update) {
        User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        update.apply(user);

        return UserDto.of(user);
    }
}
