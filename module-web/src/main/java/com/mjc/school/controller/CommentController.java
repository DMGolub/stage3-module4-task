package com.mjc.school.controller;

import com.mjc.school.service.dto.CommentRequestDto;
import com.mjc.school.service.dto.CommentResponseDto;

import java.util.List;

public interface CommentController extends BaseController<CommentRequestDto, CommentResponseDto, Long> {

	List<CommentResponseDto> readCommentsByNewsId(Long newsId);
}