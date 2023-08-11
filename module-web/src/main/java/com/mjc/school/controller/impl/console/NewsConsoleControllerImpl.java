package com.mjc.school.controller.impl.console;

import com.mjc.school.controller.interfaces.console.NewsConsoleController;
import com.mjc.school.controller.annotation.CommandBody;
import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.controller.annotation.CommandParam;
import com.mjc.school.controller.annotation.CommandQueryParams;
import com.mjc.school.controller.annotation.ConsoleController;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.dto.NewsResponseDto;
import com.mjc.school.service.query.NewsQueryParams;

import java.util.List;

@ConsoleController
public class NewsConsoleControllerImpl implements NewsConsoleController {

	private final NewsService newsService;

	public NewsConsoleControllerImpl(final NewsService newsService) {
		this.newsService = newsService;
	}

	@Override
	@CommandHandler(operation = 1)
	public List<NewsResponseDto> readAll() {
		return newsService.readAll();
	}

	@Override
	@CommandHandler(operation = 2)
	public NewsResponseDto readById(@CommandParam(name = "id") final Long id) {
		return newsService.readById(id);
	}

	@Override
	@CommandHandler(operation = 3)
	public List<NewsResponseDto> readNewsByParams(@CommandQueryParams final NewsQueryParams newsQueryParams) {
		return newsService.readNewsByParams(newsQueryParams);
	}

	@Override
	@CommandHandler(operation = 4)
	public NewsResponseDto create(@CommandBody final NewsRequestDto request) {
		return newsService.create(request);
	}

	@Override
	@CommandHandler(operation = 5)
	public NewsResponseDto update(@CommandBody final NewsRequestDto request) {
		return newsService.update(request);
	}

	@Override
	@CommandHandler(operation = 6)
	public boolean deleteById(@CommandParam(name = "id") final Long id) {
		return newsService.deleteById(id);
	}
}