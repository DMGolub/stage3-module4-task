package com.mjc.school.controller.interfaces;

import com.mjc.school.service.dto.TagRequestDto;
import com.mjc.school.service.dto.TagResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SuppressWarnings("unused")
public interface TagController extends BaseController<TagResponseDto, TagRequestDto, Long> {

	ResponseEntity<List<TagResponseDto>> readTagsByNewsId(Long newsId);
}