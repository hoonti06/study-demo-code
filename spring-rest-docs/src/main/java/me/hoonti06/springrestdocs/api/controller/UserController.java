package me.hoonti06.springrestdocs.api.controller;

import lombok.RequiredArgsConstructor;
import me.hoonti06.springrestdocs.domain.Gender;
import me.hoonti06.springrestdocs.response.ApiResponseDto;
import me.hoonti06.springrestdocs.service.UserService;
import me.hoonti06.springrestdocs.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;

    @GetMapping("/v1/users")
    public ApiResponseDto<List<UserDto>> findAll() {
        return ApiResponseDto.createOK(userService.findAll());
    }

    @GetMapping("/v1/users/search")
    public ApiResponseDto<List<UserDto> > search(@RequestParam(value="gender", required = false) Gender gender) {
        if(gender != null)
            return ApiResponseDto.createOK(userService.findByGender(gender));
        else
            return ApiResponseDto.createOK(userService.findAll());
    }

    @GetMapping("/v1/users/{id}")
    public ApiResponseDto<UserDto> findById(@PathVariable("id") Long id) {

        return ApiResponseDto.createOK(userService.findById(id));
    }

    @DeleteMapping("/v1/users/{id}")
    public ApiResponseDto<String> delete(@PathVariable("id") Long id) {

        userService.delete(id);

        return ApiResponseDto.DEFAULT_OK;
    }
}


