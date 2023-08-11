package com.mjc.school.controller.interfaces.console;

import com.mjc.school.service.dto.CommentRequestDto;
import com.mjc.school.service.dto.CommentResponseDto;

import java.util.List;

@SuppressWarnings("unused")
public interface CommentConsoleController extends BaseConsoleController<CommentRequestDto, CommentResponseDto, Long> {

	List<CommentResponseDto> readCommentsByNewsId(Long newsId);
}