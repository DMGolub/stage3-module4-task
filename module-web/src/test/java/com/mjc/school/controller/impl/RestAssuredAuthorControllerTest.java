package com.mjc.school.controller.impl;

import com.mjc.school.controller.ControllerTestConfig;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.dto.AuthorRequestDto;
import com.mjc.school.service.dto.AuthorResponseDto;
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
class RestAssuredAuthorControllerTest {

	private static final String BASE_URI = "http://localhost";
	private static final String REQUEST_MAPPING_URI = "/api/v1";

	@Autowired
	private AuthorService authorService;
	@LocalServerPort
	private int port;
	private List<AuthorResponseDto> authors;

	@BeforeEach
	public void setUp() {
		reset(authorService);

		RestAssured.baseURI = BASE_URI;
		RestAssured.port = port;
		RestAssured.basePath = REQUEST_MAPPING_URI;

		final LocalDateTime date = LocalDateTime.now();
		authors = Arrays.asList(
			new AuthorResponseDto(1L, "Name One", date, date),
			new AuthorResponseDto(2L, "Name Two", date, date)
		);
	}

	@Test
	void readAll_shouldReturn200_whenRequestIsCorrect() {
		when(authorService.readAll()).thenReturn(authors);
		final int EXPECTED_STATUS_CODE = 200;

		RestAssured.given()
			.get("/authors")
			.then().assertThat().statusCode(EXPECTED_STATUS_CODE);
		verify(authorService, times(1)).readAll(10, 0, "id::asc");
	}

	@Test
	void readById_shouldReturn200_whenRequestIsCorrectAndEntityExists() {
		final long authorId = 2L;
		when(authorService.readById(authorId)).thenReturn(authors.get((int) (authorId - 1)));
		final int EXPECTED_STATUS_CODE = 200;

		RestAssured.given()
			.get("/authors/" + authorId)
			.then().assertThat().statusCode(EXPECTED_STATUS_CODE);
		verify(authorService, times(1)).readById(authorId);
	}

	@Test
	void readByNewsId_shouldReturn200_whenRequestIsCorrectAndNewsExists() {
		final long newsId = 1L;
		final long authorId = 1L;
		when(authorService.readAuthorByNewsId(newsId)).thenReturn(authors.get((int) (authorId - 1)));
		final int EXPECTED_STATUS_CODE = 200;

		RestAssured.given()
			.get("/news/" + newsId + "/author")
			.then().assertThat().statusCode(EXPECTED_STATUS_CODE);
		verify(authorService, times(1)).readAuthorByNewsId(newsId);
	}

	@Test
	void create_shouldReturn201_whenRequestIsCorrect() {
		final String name = "New Name";
		final AuthorRequestDto request = new AuthorRequestDto(null, name);
		final LocalDateTime date = LocalDateTime.now();
		final AuthorResponseDto created = new AuthorResponseDto((long) (authors.size() + 1), name, date, date);
		when(authorService.create(request)).thenReturn(created);
		final int EXPECTED_STATUS_CODE = 201;

		RestAssured.given()
			.contentType("application/json")
			.body(Map.of("name", name))
			.when().post("/authors")
			.then().statusCode(EXPECTED_STATUS_CODE);
		verify(authorService, times(1)).create(request);
	}

	@Test
	void update_shouldReturn200_whenRequestIsCorrect() {
		final long authorId = 2L;
		final String updatedName = "Updated Name";
		final AuthorRequestDto request = new AuthorRequestDto(authorId, updatedName);
		final LocalDateTime date = LocalDateTime.now();
		final AuthorResponseDto updated = new AuthorResponseDto(authorId, updatedName, date, date);
		when(authorService.update(request)).thenReturn(updated);
		final int EXPECTED_STATUS_CODE = 200;

		RestAssured.given()
			.contentType("application/json")
			.body(Map.of("id", String.valueOf(authorId), "name", updatedName))
			.when().patch("/authors/" + authorId)
			.then().statusCode(EXPECTED_STATUS_CODE);
		verify(authorService, times(1)).update(request);
	}

	@Test
	void deleteById_shouldReturn204_whenRequestIsCorrect() {
		final long authorId = 1L;
		when(authorService.deleteById(authorId)).thenReturn(true);
		final int EXPECTED_STATUS_CODE = 204;

		RestAssured.given()
			.delete("/authors/" + authorId)
			.then().assertThat().statusCode(EXPECTED_STATUS_CODE);
		verify(authorService, times(1)).deleteById(authorId);
	}
}