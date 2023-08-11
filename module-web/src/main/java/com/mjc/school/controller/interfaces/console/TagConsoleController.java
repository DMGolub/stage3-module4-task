package com.mjc.school.controller.interfaces.console;

import com.mjc.school.service.dto.TagRequestDto;
import com.mjc.school.service.dto.TagResponseDto;

import java.util.List;

@SuppressWarnings("unused")
public interface TagConsoleController extends BaseConsoleController<TagRequestDto, TagResponseDto, Long> {

	List<TagResponseDto> readTagsByNewsId(Long newsId);
}