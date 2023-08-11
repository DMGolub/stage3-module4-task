package com.mjc.school.controller.impl.console;

import com.mjc.school.controller.interfaces.console.TagConsoleController;
import com.mjc.school.controller.annotation.CommandBody;
import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.controller.annotation.CommandParam;
import com.mjc.school.controller.annotation.ConsoleController;
import com.mjc.school.service.TagService;
import com.mjc.school.service.dto.TagRequestDto;
import com.mjc.school.service.dto.TagResponseDto;

import java.util.List;

@ConsoleController
public class TagConsoleControllerImpl implements TagConsoleController {

	private final TagService tagService;

	public TagConsoleControllerImpl(final TagService tagService) {
		this.tagService = tagService;
	}

	@Override
	@CommandHandler(operation = 13)
	public List<TagResponseDto> readAll() {
		return tagService.readAll();
	}

	@Override
	@CommandHandler(operation = 14)
	public TagResponseDto readById(@CommandParam(name = "id") final Long id) {
		return tagService.readById(id);
	}

	@Override
	@CommandHandler(operation = 15)
	public List<TagResponseDto> readTagsByNewsId(@CommandParam(name = "newsId") final Long newsId) {
		return tagService.readTagsByNewsId(newsId);
	}

	@Override
	@CommandHandler(operation = 16)
	public TagResponseDto create(@CommandBody final TagRequestDto request) {
		return tagService.create(request);
	}

	@Override
	@CommandHandler(operation = 17)
	public TagResponseDto update(@CommandBody final TagRequestDto request) {
		return tagService.update(request);
	}

	@Override
	@CommandHandler(operation = 18)
	public boolean deleteById(@CommandParam(name = "id") final Long id) {
		return tagService.deleteById(id);
	}
}