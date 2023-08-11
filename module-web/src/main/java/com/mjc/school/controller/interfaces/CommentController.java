package com.mjc.school.controller.interfaces;

import com.mjc.school.service.dto.CommentRequestDto;
import com.mjc.school.service.dto.CommentResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SuppressWarnings("unused")
public interface CommentController extends BaseController<CommentResponseDto, CommentRequestDto, Long> {

	ResponseEntity<List<CommentResponseDto>> readCommentsByNewsId(Long newsId);
}