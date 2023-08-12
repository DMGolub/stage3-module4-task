package com.mjc.school.controller.impl;

import com.mjc.school.controller.ControllerTestConfig;
import com.mjc.school.service.CommentService;
import com.mjc.school.service.dto.CommentRequestDto;
import com.mjc.school.service.dto.CommentResponseDto;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {ControllerTestConfig.class})
class RestAssuredCommentControllerTest {

	private static final String BASE_URI = "http://localhost";
	private static final String REQUEST_MAPPING_URI = "/api/v1";

	@Autowired
	private CommentService commentService;
	@LocalServerPort
	private int port;
	private List<CommentResponseDto> comments;

	@BeforeEach
	public void setUp() {
		reset(commentService);

		RestAssured.baseURI = BASE_URI;
		RestAssured.port = port;
		RestAssured.basePath = REQUEST_MAPPING_URI;

		final long newsId = 1L;
		final LocalDateTime date = LocalDateTime.now();
		comments = Arrays.asList(
			new CommentResponseDto(1L, "Content One", newsId, date, date),
			new CommentResponseDto(2L, "Content Two", newsId, date, date)
		);
	}

	@Test
	void readAll_shouldReturn200_whenRequestIsCorrect() {
		when(commentService.readAll()).thenReturn(comments);
		final int EXPECTED_STATUS_CODE = 200;

		RestAssured.given()
			.get("/comments")
			.then().assertThat().statusCode(EXPECTED_STATUS_CODE);
		verify(commentService, times(1)).readAll(10, 0, "id::asc");
	}

	@Test
	void readById_shouldReturn200_whenRequestIsCorrectAndEntityExists() {
		final long commentId = 2L;
		when(commentService.readById(commentId)).thenReturn(comments.get((int) (commentId - 1)));
		final int EXPECTED_STATUS_CODE = 200;

		RestAssured.given()
			.get("/comments/" + commentId)
			.then().assertThat().statusCode(EXPECTED_STATUS_CODE);
		verify(commentService, times(1)).readById(commentId);
	}

	@Test
	void readCommentsByNewsId_shouldReturn200_whenRequestIsCorrectAndNewsExists() {
		final long newsId = 1L;
		when(commentService.readCommentsByNewsId(newsId)).thenReturn(comments);
		final int EXPECTED_STATUS_CODE = 200;

		RestAssured.given()
			.get("/news/" + newsId + "/comments")
			.then().assertThat().statusCode(EXPECTED_STATUS_CODE);
		verify(commentService, times(1)).readCommentsByNewsId(newsId);
	}

	@Test
	void create_shouldReturn201_whenRequestIsCorrect() {
		final String content = "New Content";
		final long newsId = 1L;
		final CommentRequestDto request = new CommentRequestDto(null, content, newsId);
		final LocalDateTime date = LocalDateTime.now();
		final CommentResponseDto created =
			new CommentResponseDto((long) (comments.size() + 1), content, newsId, date, date);
		when(commentService.create(request)).thenReturn(created);
		final int EXPECTED_STATUS_CODE = 201;

		RestAssured.given()
			.contentType("application/json")
			.body(Map.of(
				"content", content,
				"newsId", String.valueOf(newsId)
				)
			)
			.when().post("/comments")
			.then().statusCode(EXPECTED_STATUS_CODE);
		verify(commentService, times(1)).create(request);
	}

	@Test
	void update_shouldReturn200_whenRequestIsCorrect() {
		final long commentId = 2L;
		final String updatedComment = "Updated Comment";
		final long newsId = 1L;
		final CommentRequestDto request = new CommentRequestDto(commentId, updatedComment, newsId);
		final LocalDateTime date = LocalDateTime.now();
		final CommentResponseDto updated =
			new CommentResponseDto(commentId, updatedComment, newsId, date, date);
		when(commentService.update(request)).thenReturn(updated);
		final int EXPECTED_STATUS_CODE = 200;

		RestAssured.given()
			.contentType("application/json")
			.body(Map.of(
				"id", String.valueOf(commentId),
				"content", updatedComment,
				"newsId", newsId
				)
			)
			.when().patch("/comments/" + commentId)
			.then().statusCode(EXPECTED_STATUS_CODE);
		verify(commentService, times(1)).update(request);
	}

	@Test
	void deleteById_shouldReturn204_whenRequestIsCorrect() {
		final long commentId = 1L;
		when(commentService.deleteById(commentId)).thenReturn(true);
		final int EXPECTED_STATUS_CODE = 204;

		RestAssured.given()
			.delete("/comments/" + commentId)
			.then().assertThat().statusCode(EXPECTED_STATUS_CODE);
		verify(commentService, times(1)).deleteById(commentId);
	}
}