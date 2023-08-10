package com.mjc.school.controller.impl;

import com.mjc.school.service.dto.CommentRequestDto;
import com.mjc.school.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CommentControllerImplTest {

	@Mock
	private CommentServiceImpl commentService;
	@InjectMocks
	private CommentControllerImpl commentController;

	@Test
	void create_shouldInvokeServiceSaveMethod_whenInvoked() {
		final CommentRequestDto request = new CommentRequestDto(null, "Content", null);

		commentController.create(request);
		verify(commentService, times(1)).create(request);
	}

	@Test
	void readAll_shouldInvokeServiceGetAllMethod_whenInvoked() {
		commentController.readAll();
		verify(commentService, times(1)).readAll();
	}

	@Test
	void readById_shouldInvokeServiceGetByIdMethod_whenInvoked() {
		final long id = 1L;

		commentController.readById(id);

		verify(commentService, times(1)).readById(id);
	}

	@Test
	void update_shouldInvokeServiceUpdateMethod_whenInvoked() {
		final CommentRequestDto request = new CommentRequestDto(15L, "Content", null);

		commentController.update(request);
		verify(commentService, times(1)).update(request);
	}

	@Test
	void deleteById_shouldInvokeServiceDelete_whenInvoked() {
		final long id = 1L;

		commentController.deleteById(id);

		verify(commentService, times(1)).deleteById(id);
	}
}