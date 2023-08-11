package com.mjc.school.controller.impl.console;

import com.mjc.school.controller.interfaces.console.AuthorConsoleController;
import com.mjc.school.controller.annotation.CommandBody;
import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.controller.annotation.CommandParam;
import com.mjc.school.controller.annotation.ConsoleController;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.dto.AuthorResponseDto;

import java.util.List;

@ConsoleController
public class AuthorConsoleControllerImpl implements AuthorConsoleController {

	private final AuthorService authorService;

	public AuthorConsoleControllerImpl(final AuthorService authorService) {
		this.authorService = authorService;
	}

	@Override
	@CommandHandler(operation = 7)
	public List<AuthorResponseDto> readAll() {
		return authorService.readAll();
	}

	@Override
	@CommandHandler(operation = 8)
	public AuthorResponseDto readById(@CommandParam(name = "id") final Long id) {
		return authorService.readById(id);
	}

	@Override
	@CommandHandler(operation = 9)
	public AuthorResponseDto readByNewsId(@CommandParam(name = "id") final Long newsId) {
		return authorService.readAuthorByNewsId(newsId);
	}

	@Override
	@CommandHandler(operation = 10)
	public AuthorResponseDto create(@CommandBody final AuthorRequestDto request) {
		return authorService.create(request);
	}

	@Override
	@CommandHandler(operation = 11)
	public AuthorResponseDto update(@CommandBody final AuthorRequestDto request) {
		return authorService.update(request);
	}

	@Override
	@CommandHandler(operation = 12)
	public boolean deleteById(@CommandParam(name = "id") final Long id) {
		return authorService.deleteById(id);
	}
}