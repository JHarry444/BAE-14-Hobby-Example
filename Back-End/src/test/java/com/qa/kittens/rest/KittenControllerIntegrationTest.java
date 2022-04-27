package com.qa.kittens.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.kittens.data.Kitten;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) // try random ports until it finds a free one
@AutoConfigureMockMvc
// loads both sql scripts from the resources folder and executes them BEFORE each test
@Sql(scripts = { "classpath:kitten-schema.sql",
		"classpath:kitten-data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("test") // sets profile for the test class
class KittenControllerIntegrationTest {

	@Autowired // tells Spring to inject the MockMVC object into this class
	private MockMvc mockMVC;

	@Autowired
	private ObjectMapper mapper; // yanks the class Spring uses to convert java to JSON

	@Test
	void testCreate() throws Exception {
		Kitten testKit = new Kitten("Jess", "Black and White", 57, 10);
		// convert to json
		String testKitAsJSON = this.mapper.writeValueAsString(testKit);

		System.out.println(testKit);
		System.out.println(testKitAsJSON);

		// body, method, address and the content-type header
		RequestBuilder request = post("/createKitten").contentType(MediaType.APPLICATION_JSON).content(testKitAsJSON);

		// check the response body and status

		ResultMatcher checkStatus = status().is(201);

		Kitten testCreatedKit = new Kitten("Jess", "Black and White", 57, 10);
		testCreatedKit.setId(2); // due to the AUTO_INCREMENT
		String testCreatedKitAsJSON = this.mapper.writeValueAsString(testCreatedKit);

		ResultMatcher checkBody = content().json(testCreatedKitAsJSON);
		// SEND request and check status + body
		this.mockMVC.perform(request).andExpect(checkStatus).andExpect(checkBody);
	}

	@Test
	void testCreateAbridged() throws Exception {
		this.mockMVC
				.perform(post("/createKitten").contentType(MediaType.APPLICATION_JSON)
						.content(this.mapper.writeValueAsString(new Kitten("Jess", "Black and White", 57, 10))))
				.andExpect(status().is(201)).andExpect(content()
						.json(this.mapper.writeValueAsString(new Kitten(2, "Jess", "Black and White", 57, 10))));
	}

	@Test
	void testUpdate() throws Exception {
		int id = 1;
		Kitten newKitten = new Kitten(id, "Jess", "Black and White", 7, 8);
		String newKittenAsJSON = this.mapper.writeValueAsString(newKitten);

		RequestBuilder req = put("/replaceKitten/" + id).contentType(MediaType.APPLICATION_JSON)
				.content(newKittenAsJSON);

		ResultMatcher checkStatus = status().isAccepted();

		ResultMatcher checkBody = content().json(newKittenAsJSON);

		this.mockMVC.perform(req).andExpect(checkStatus).andExpect(checkBody);
	}

	@Test
	void findById() throws Exception {
		RequestBuilder req = get("/getKitten/1");

		ResultMatcher checkStatus = status().isOk();

		Kitten testKitten = new Kitten(1, "Tiddles III", "Maine Coon", 12, 11);

		String testKittenAsJSON = this.mapper.writeValueAsString(testKitten);

		ResultMatcher checkBody = content().json(testKittenAsJSON);

		this.mockMVC.perform(req).andExpect(checkStatus).andExpect(checkBody);

		// alternative method
		MvcResult result = this.mockMVC.perform(req).andExpect(checkStatus).andReturn();

		String responseBody = result.getResponse().getContentAsString();

		Kitten resultKitten = this.mapper.readValue(responseBody, Kitten.class);

		assertThat(testKitten).isEqualTo(resultKitten);
	}

	@Test
	void testFindByName() throws Exception {
		RequestBuilder request = get("/getByName/Tiddles III");

		ResultMatcher checkStatus = status().isOk();

		List<Kitten> testKittens = List.of(new Kitten(1, "Tiddles III", "Maine Coon", 12, 11));

		String testKittensAsJSON = this.mapper.writeValueAsString(testKittens);

		ResultMatcher checkBody = content().json(testKittensAsJSON);

		this.mockMVC.perform(request).andExpect(checkStatus).andExpect(checkBody);
	}

	@Test
	void testDelete() throws Exception {
		// create request
		RequestBuilder request = delete("/deleteKitten/1");

		// check response
		ResultMatcher checkStatus = status().is(204);
		ResultMatcher checkBody = content().string("Deleted: 1");

		this.mockMVC.perform(request).andExpect(checkStatus).andExpect(checkBody);
	}
}
