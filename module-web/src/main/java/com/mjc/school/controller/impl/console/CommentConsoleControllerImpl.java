package com.mjc.school.controller.impl.console;

import com.mjc.school.controller.interfaces.console.CommentConsoleController;
import com.mjc.school.controller.annotation.CommandBody;
import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.controller.annotation.CommandParam;
import com.mjc.school.controller.annotation.ConsoleController;
import com.mjc.school.service.CommentService;
import com.mjc.school.service.dto.CommentRequestDto;
import com.mjc.school.service.dto.CommentResponseDto;

import java.util.List;

@ConsoleController
public class CommentConsoleControllerImpl implements CommentConsoleController {

	private final CommentService commentService;

	public CommentConsoleControllerImpl(final CommentService commentService) {
		this.commentService = commentService;
	}

	@Override
	@CommandHandler(operation = 19)
	public List<CommentResponseDto> readAll() {
		return commentService.readAll();
	}

	@Override
	@CommandHandler(operation = 20)
	public CommentResponseDto readById(@CommandParam(name = "id") final Long id) {
		return commentService.readById(id);
	}


	@Override
	@CommandHandler(operation = 21)
	public List<CommentResponseDto> readCommentsByNewsId(@CommandParam(name = "newsId") final Long newsId) {
		return commentService.readCommentsByNewsId(newsId);
	}

	@Override
	@CommandHandler(operation = 22)
	public CommentResponseDto create(@CommandBody final CommentRequestDto request) {
		return commentService.create(request);
	}

	@Override
	@CommandHandler(operation = 23)
	public CommentResponseDto update(@CommandBody final CommentRequestDto request) {
		return commentService.update(request);
	}

	@Override
	@CommandHandler(operation = 24)
	public boolean deleteById(@CommandParam(name = "id") final Long id) {
		return commentService.deleteById(id);
	}
}