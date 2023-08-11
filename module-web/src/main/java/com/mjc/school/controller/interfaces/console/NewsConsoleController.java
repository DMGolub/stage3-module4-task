package com.mjc.school.controller.interfaces.console;

import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.dto.NewsResponseDto;
import com.mjc.school.service.query.NewsQueryParams;

import java.util.List;

@SuppressWarnings("unused")
public interface NewsConsoleController extends BaseConsoleController<NewsRequestDto, NewsResponseDto, Long> {

	List<NewsResponseDto> readNewsByParams(NewsQueryParams newsQueryParams);
}