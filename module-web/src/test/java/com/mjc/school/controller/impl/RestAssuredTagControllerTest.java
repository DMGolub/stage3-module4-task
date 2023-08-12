package com.mjc.school.controller.impl;

import com.mjc.school.controller.ControllerTestConfig;
import com.mjc.school.service.TagService;
import com.mjc.school.service.dto.TagRequestDto;
import com.mjc.school.service.dto.TagResponseDto;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {ControllerTestConfig.class})
class RestAssuredTagControllerTest {

	private static final String BASE_URI = "http://localhost";
	private static final String REQUEST_MAPPING_URI = "/api/v1";

	@Autowired
	private TagService tagService;
	@LocalServerPort
	private int port;
	private List<TagResponseDto> tags;

	@BeforeEach
	public void setUp() {
		reset(tagService);

		RestAssured.baseURI = BASE_URI;
		RestAssured.port = port;
		RestAssured.basePath = REQUEST_MAPPING_URI;

		tags = Arrays.asList(
			new TagResponseDto(1L, "Name One"),
			new TagResponseDto(2L, "Name Two")
		);
	}

	@Test
	void readAll_shouldReturn200_whenRequestIsCorrect() {
		when(tagService.readAll()).thenReturn(tags);
		final int EXPECTED_STATUS_CODE = 200;

		RestAssured.given()
			.get("/tags")
			.then().assertThat().statusCode(EXPECTED_STATUS_CODE);
		verify(tagService, times(1)).readAll(10, 0, "id::asc");
	}

	@Test
	void readById_shouldReturn200_whenRequestIsCorrectAndEntityExists() {
		final long tagId = 2L;
		when(tagService.readById(tagId)).thenReturn(tags.get((int) (tagId - 1)));
		final int EXPECTED_STATUS_CODE = 200;

		RestAssured.given()
			.get("/tags/" + tagId)
			.then().assertThat().statusCode(EXPECTED_STATUS_CODE);
		verify(tagService, times(1)).readById(tagId);
	}

	@Test
	void readByNewsId_shouldReturn200_whenRequestIsCorrectAndNewsExists() {
		final long newsId = 1L;
		when(tagService.readTagsByNewsId(newsId)).thenReturn(tags);
		final int EXPECTED_STATUS_CODE = 200;

		RestAssured.given()
			.get("/news/" + newsId + "/tags")
			.then().assertThat().statusCode(EXPECTED_STATUS_CODE);
		verify(tagService, times(1)).readTagsByNewsId(newsId);
	}

	@Test
	void create_shouldReturn201_whenRequestIsCorrect() {
		final String name = "New Name";
		final TagRequestDto request = new TagRequestDto(null, name);
		final TagResponseDto created = new TagResponseDto((long) (tags.size() + 1), name);
		when(tagService.create(request)).thenReturn(created);
		final int EXPECTED_STATUS_CODE = 201;

		RestAssured.given()
			.contentType("application/json")
			.body(Map.of("name", name))
			.when().post("/tags")
			.then().statusCode(EXPECTED_STATUS_CODE);
		verify(tagService, times(1)).create(request);
	}

	@Test
	void update_shouldReturn200_whenRequestIsCorrect() {
		final long tagId = 2L;
		final String updatedName = "Updated Name";
		final TagRequestDto request = new TagRequestDto(tagId, updatedName);
		final TagResponseDto updated = new TagResponseDto(tagId, updatedName);
		when(tagService.update(request)).thenReturn(updated);
		final int EXPECTED_STATUS_CODE = 200;

		RestAssured.given()
			.contentType("application/json")
			.body(Map.of("id", String.valueOf(tagId), "name", updatedName))
			.when().patch("/tags/" + tagId)
			.then().statusCode(EXPECTED_STATUS_CODE);
		verify(tagService, times(1)).update(request);
	}

	@Test
	void deleteById_shouldReturn204_whenRequestIsCorrect() {
		final long tagId = 1L;
		when(tagService.deleteById(tagId)).thenReturn(true);
		final int EXPECTED_STATUS_CODE = 204;

		RestAssured.given()
			.delete("/tags/" + tagId)
			.then().assertThat().statusCode(EXPECTED_STATUS_CODE);
		verify(tagService, times(1)).deleteById(tagId);
	}
}