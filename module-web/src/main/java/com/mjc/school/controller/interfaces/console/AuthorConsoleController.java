package com.mjc.school.controller.interfaces.console;

import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.dto.AuthorResponseDto;

@SuppressWarnings("unused")
public interface AuthorConsoleController extends BaseConsoleController<AuthorRequestDto, AuthorResponseDto, Long> {

	AuthorResponseDto readByNewsId(Long newsId);
}