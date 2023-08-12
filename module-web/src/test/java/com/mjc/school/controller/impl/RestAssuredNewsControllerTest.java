package com.mjc.school.controller.impl;

import com.mjc.school.controller.ControllerTestConfig;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.dto.NewsRequestDto;
import com.mjc.school.service.dto.NewsResponseDto;
import com.mjc.school.service.query.NewsQueryParams;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {ControllerTestConfig.class})
class RestAssuredNewsControllerTest {

	private static final String BASE_URI = "http://localhost";
	private static final String REQUEST_MAPPING_URI = "/api/v1";

	@Autowired
	private NewsService newsService;
	@LocalServerPort
	private int port;
	private List<NewsResponseDto> news;

	@BeforeEach
	public void setUp() {
		reset(newsService);

		RestAssured.baseURI = BASE_URI;
		RestAssured.port = port;
		RestAssured.basePath = REQUEST_MAPPING_URI;

		final LocalDateTime date = LocalDateTime.now();
		final long authorId = 1L;
		final NewsResponseDto news1 = new NewsResponseDto(
			1L,
			"Title One",
			"Content One",
			date,
			date,
			authorId,
			new ArrayList<>(),
			new ArrayList<>()
		);
		final NewsResponseDto news2 = new NewsResponseDto(
			2L,
			"Title Two",
			"Content Two",
			date,
			date,
			authorId,
			new ArrayList<>(),
			new ArrayList<>()
		);
		news = Arrays.asList(news1, news2);
	}

	@Test
	void readAll_shouldReturn200_whenRequestIsCorrect() {
		when(newsService.readAll()).thenReturn(news);
		final int EXPECTED_STATUS_CODE = 200;

		RestAssured.given()
			.get("/news")
			.then().assertThat().statusCode(EXPECTED_STATUS_CODE);
		verify(newsService, times(1)).readAll(10, 0, "id::asc");
	}

	@Test
	void readById_shouldReturn200_whenRequestIsCorrectAndEntityExists() {
		final long newsId = 2L;
		when(newsService.readById(newsId)).thenReturn(news.get((int) (newsId - 1)));
		final int EXPECTED_STATUS_CODE = 200;

		RestAssured.given()
			.get("/news/" + newsId)
			.then().assertThat().statusCode(EXPECTED_STATUS_CODE);
		verify(newsService, times(1)).readById(newsId);
	}

	@Test
	void readNewsByParams_shouldReturn200_whenRequestIsCorrects() {
		final String authorName = "Author Name";
		final NewsQueryParams params =
			new NewsQueryParams(null, null, authorName, null, null);
		when(newsService.readNewsByParams(params)).thenReturn(news);
		final int EXPECTED_STATUS_CODE = 200;

		RestAssured.given()
			.get("/news/search?author_name=Author Name")
			.then().assertThat().statusCode(EXPECTED_STATUS_CODE);
		verify(newsService, times(1)).readNewsByParams(params);
	}

	@Test
	void create_shouldReturn201_whenRequestIsCorrect() {
		final String title = "New Name";
		final String content = "New Content";
		final long authorId = 1L;
		final NewsRequestDto request = new NewsRequestDto(null, title, content, authorId, null);
		final LocalDateTime date = LocalDateTime.now();
		final List<Long> tags = new ArrayList<>();
		final List<Long> comments = new ArrayList<>();
		final NewsResponseDto created =
			new NewsResponseDto((long) (news.size() + 1), title, content, date, date, authorId, tags, comments);
		when(newsService.create(request)).thenReturn(created);
		final int EXPECTED_STATUS_CODE = 201;

		RestAssured.given()
			.contentType("application/json")
			.body(Map.of(
				"title", title,
				"content", content,
				"authorId", String.valueOf(authorId)
				)
			)
			.when().post("/news")
			.then().statusCode(EXPECTED_STATUS_CODE);
		verify(newsService, times(1)).create(request);
	}

	@Test
	void update_shouldReturn200_whenRequestIsCorrect() {
		final long newsId = 2L;
		final String updatedTitle = "Updated Title";
		final String updatedContent = "Updated Content";
		final long authorId = 1L;
		final NewsRequestDto request =
			new NewsRequestDto(newsId, updatedTitle, updatedContent, authorId, null);
		final LocalDateTime date = LocalDateTime.now();
		final List<Long> tags = new ArrayList<>();
		final List<Long> comments = new ArrayList<>();
		final NewsResponseDto updated =
			new NewsResponseDto(newsId, updatedTitle, updatedContent, date, date, authorId, tags, comments);
		when(newsService.update(request)).thenReturn(updated);
		final int EXPECTED_STATUS_CODE = 200;

		RestAssured.given()
			.contentType("application/json")
			.body(Map.of(
				"id", String.valueOf(newsId),
				"title", updatedTitle,
				"content", updatedContent,
				"authorId", String.valueOf(authorId)
				)
			)
			.when().patch("/news/" + newsId)
			.then().statusCode(EXPECTED_STATUS_CODE);
		verify(newsService, times(1)).update(request);
	}

	@Test
	void deleteById_shouldReturn204_whenRequestIsCorrect() {
		final long newsId = 1L;
		when(newsService.deleteById(newsId)).thenReturn(true);
		final int EXPECTED_STATUS_CODE = 204;

		RestAssured.given()
			.delete("/news/" + newsId)
			.then().assertThat().statusCode(EXPECTED_STATUS_CODE);
		verify(newsService, times(1)).deleteById(newsId);
	}
}