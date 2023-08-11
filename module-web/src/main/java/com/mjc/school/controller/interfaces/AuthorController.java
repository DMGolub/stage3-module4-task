package com.mjc.school.controller.interfaces;

import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.dto.AuthorResponseDto;
import org.springframework.http.ResponseEntity;

@SuppressWarnings("unused")
public interface AuthorController extends BaseController<AuthorResponseDto, AuthorRequestDto, Long> {

	ResponseEntity<AuthorResponseDto> readByNewsId(Long newsId);
}