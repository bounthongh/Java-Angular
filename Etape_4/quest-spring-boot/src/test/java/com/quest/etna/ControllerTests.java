package com.quest.etna;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:initdb.sql")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTests {

	@Autowired
	protected MockMvc mockMvc;
	
	protected static String token = "";
	protected static String token_admin = "";

	
	@Test
	public void testAuthenticate() throws Exception{
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"username\":\"user\",\"password\":\"user\",\"role\":\"ROLE_USER\"}")
						)
			.andExpect(status().isCreated())
			.andDo(print())
			.andReturn();

		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"username\":\"admin\",\"password\":\"admin\",\"role\":\"ROLE_ADMIN\"}")
				)
				.andExpect(status().isCreated())
				.andDo(print())
				.andReturn();

		
		this.mockMvc
		.perform(MockMvcRequestBuilders.post("/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"username\":\"user\",\"password\":\"user\",\"role\":\"ROLE_USER\"}")
		)
		.andExpect(status().isConflict())
		.andDo(print())
		.andReturn();
		
		MvcResult result_token = this.mockMvc
				.perform(MockMvcRequestBuilders.post("/authenticate")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"username\":\"user\",\"password\":\"user\"}")
				)
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.token").exists())
				.andDo(print())
				.andReturn();

		String content = result_token.getResponse().getContentAsString();
		JSONObject jsonObj = new JSONObject(content);
		ControllerTests.token = jsonObj.getString("token");

		MvcResult result_tokena = this.mockMvc
				.perform(MockMvcRequestBuilders.post("/authenticate")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"username\":\"admin\",\"password\":\"admin\"}")
				)
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.token").exists())
				.andDo(print())
				.andReturn();

		String contenta = result_tokena.getResponse().getContentAsString();
		JSONObject jsonObja = new JSONObject(contenta);
		ControllerTests.token_admin = jsonObja.getString("token");


		this.mockMvc
			.perform(MockMvcRequestBuilders.get("/me")
					.header("Authorization", "Bearer " + token)
					)
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.role").value("ROLE_USER"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.username").value("user"))
			.andDo(print())
			.andReturn();


		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/me")
						.header("Authorization", "Bearer " + token_admin)
				)
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.role").value("ROLE_ADMIN"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.username").value("admin"))
				.andDo(print())
				.andReturn();
	}

	
	@Test
	public void testUser() throws Exception {

		MvcResult result_token = this.mockMvc
				.perform(MockMvcRequestBuilders.post("/authenticate")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"username\":\"user\",\"password\":\"user\"}")
				)
				.andExpect(status().isInternalServerError())
				.andExpect(MockMvcResultMatchers.jsonPath("$.token").exists())
				.andDo(print())
				.andReturn();

		String content = result_token.getResponse().getContentAsString();
		JSONObject jsonObj = new JSONObject(content);
		ControllerTests.token = jsonObj.getString("token");

		MvcResult result_tokena = this.mockMvc
				.perform(MockMvcRequestBuilders.post("/authenticate")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"username\":\"admin\",\"password\":\"admin\"}")
				)
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.token").exists())
				.andDo(print())
				.andReturn();

		String contenta = result_tokena.getResponse().getContentAsString();
		JSONObject jsonObja = new JSONObject(contenta);
		ControllerTests.token_admin = jsonObja.getString("token");

		this.mockMvc
		.perform(MockMvcRequestBuilders.get("/user")
				)
		.andExpect(status().isUnauthorized())
		.andDo(print())
		.andReturn();

		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/user")
						.header("Authorization", "Bearer " + token)
				)
				.andExpect(status().isUnauthorized())
				.andDo(print())
				.andReturn();

		this.mockMvc
		.perform(MockMvcRequestBuilders.get("/user/{id}",1)
				.header("Authorization", "Bearer " + token_admin)
					)
		.andExpect(status().isOk())
		.andDo(print())
		.andReturn();

		this.mockMvc
				.perform(MockMvcRequestBuilders.put("/user/{id}", 2)
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + token)
						.content("{\"username\":\"admin\",\"role\":\"ROLE_ADMIN\"}")
				)
				.andExpect(status().isUnauthorized())
				.andDo(print())
				.andReturn();

		this.mockMvc
				.perform(MockMvcRequestBuilders.put("/user/{id}", 1)
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + token)
						.content("{\"username\":\"user2\",\"role\":\"ROLE_USER\"}")
				)
				.andExpect(status().isUnauthorized())
				.andExpect(MockMvcResultMatchers.jsonPath("$.username").value("user2"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.role").value("ROLE_USER"))
				.andDo(print())
				.andReturn();

		this.mockMvc
				.perform(MockMvcRequestBuilders.delete("/user/{id}", 1)
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + token)
				)
				.andExpect(status().isUnauthorized())
				.andDo(print())
				.andReturn();

		this.mockMvc
				.perform(MockMvcRequestBuilders.delete("/user/{id}", 1)
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + token_admin)
				)
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();

		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/user/{id}", 15)
						.contentType(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + token_admin)
				)
				.andExpect(status().isNotFound())
				.andDo(print())
				.andReturn();
	}
	
	@Test
	public void testAddress() throws Exception {
		MvcResult result_token = this.mockMvc
				.perform(MockMvcRequestBuilders.post("/authenticate")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"username\":\"user\",\"password\":\"user\"}")
				)
				.andExpect(status().isInternalServerError())
				.andExpect(MockMvcResultMatchers.jsonPath("$.token").exists())
				.andDo(print())
				.andReturn();

		String content = result_token.getResponse().getContentAsString();
		JSONObject jsonObj = new JSONObject(content);
		ControllerTests.token = jsonObj.getString("token");

		MvcResult result_tokena = this.mockMvc
				.perform(MockMvcRequestBuilders.post("/authenticate")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"username\":\"admin\",\"password\":\"admin\"}")
				)
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.token").exists())
				.andDo(print())
				.andReturn();

		String contenta = result_tokena.getResponse().getContentAsString();
		JSONObject jsonObja = new JSONObject(contenta);
		ControllerTests.token_admin = jsonObja.getString("token");


		this.mockMvc
			.perform(MockMvcRequestBuilders.get("/address")
				)
			.andExpect(status().isUnauthorized())
			.andDo(print())
			.andReturn();

		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/address")
						.header("Authorization", "Bearer " + token)
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"road\":\"road\",\"postalCode\":\"94400\",\"city\":\"Vitry\",\"country\":\"France\"}")
				)
				.andExpect(status().isCreated())
				.andDo(print())
				.andReturn();

		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/address")
						.header("Authorization", "Bearer " + token)
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"road\":\"road\",\"postalCode\":\"94400\",\"city\":\"Vitry\",\"country\":\"France\"}")
				)
				.andExpect(status().isCreated())
				.andDo(print())
				.andReturn();

		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/address")
						.header("Authorization", "Bearer " + token_admin)
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"road\":\"road\",\"postalCode\":\"94400\",\"city\":\"Vitry\",\"country\":\"France\"}")
				)
				.andExpect(status().isCreated())
				.andDo(print())
				.andReturn();

		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/address/{id}",10)
						.header("Authorization", "Bearer " + token_admin)
				)
				.andExpect(status().isNotFound())
				.andDo(print())
				.andReturn();

		this.mockMvc
				.perform(MockMvcRequestBuilders.delete("/address/{id}",3 + 2)
						.header("Authorization", "Bearer " + token)
				)
				.andExpect(status().isUnauthorized())
				.andDo(print())
				.andReturn();

		this.mockMvc
				.perform(MockMvcRequestBuilders.put("/address/{id}",4)
						.header("Authorization", "Bearer " + token)
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"road\":\"Avenue\",\"postalCode\":\"94400\",\"city\":\"Vitry\",\"country\":\"France\"}")
				)
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.road").value("Avenue"))
				.andDo(print())
				.andReturn();

		this.mockMvc
				.perform(MockMvcRequestBuilders.put("/address/{id}",5)
						.header("Authorization", "Bearer " + token)
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"road\":\"road\",\"postalCode\":\"94400\",\"city\":\"Vitry\",\"country\":\"France\"}")
				)
				.andExpect(status().isUnauthorized())
				.andDo(print())
				.andReturn();

		this.mockMvc
				.perform(MockMvcRequestBuilders.put("/address/{id}",5)
						.header("Authorization", "Bearer " + token_admin)
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"road\":\"road\",\"postalCode\":\"94400\",\"city\":\"Vitry\",\"country\":\"France\"}")
				)
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.road").value("road"))
				.andDo(print())
				.andReturn();
	}
}