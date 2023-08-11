package com.mjc.school.controller.interfaces;

import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.dto.NewsResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SuppressWarnings("unused")
public interface NewsController extends BaseController<NewsResponseDto, NewsRequestDto, Long> {

	ResponseEntity<List<NewsResponseDto>> readNewsByParams(
		List<String> tagNames,
		List<Long> tagIds,
		String authorName,
		String title,
		String content
	);
}