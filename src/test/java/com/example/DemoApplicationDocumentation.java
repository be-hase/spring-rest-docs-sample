package com.example;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(DemoApplication.class)
public class DemoApplicationDocumentation {
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private WebApplicationContext context;

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                                 .apply(documentationConfiguration(restDocumentation))
                                 .build();
    }

    @Test
    public void testGetBlog() throws Exception {
        mockMvc.perform(get("/blog/{id}", 1))
               .andExpect(status().isOk())
               .andDo(
                       document(
                               "blog/get",
                               pathParameters(parameterWithName("id").description("Blog ID")),
                               responseFields(
                                       fieldWithPath("id")
                                               .type(JsonFieldType.NUMBER)
                                               .description("Blog ID"),
                                       fieldWithPath("title")
                                               .type(JsonFieldType.STRING)
                                               .description("Blog Title"),
                                       fieldWithPath("author")
                                               .type(JsonFieldType.STRING)
                                               .description("Blog Author")
                               )
                       )
               );
    }

    @Test
    public void testPostBlog() throws Exception {
        Map<String, Object> body = new HashMap<>();
        body.put("title", "new title");
        body.put("author", "new author");

        mockMvc.perform(post("/blog")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsBytes(body)))
               .andExpect(status().isOk())
               .andDo(
                       document(
                               "blog/post",
                               requestFields(
                                       fieldWithPath("title")
                                               .type(JsonFieldType.STRING)
                                               .description("Blog Title"),
                                       fieldWithPath("author")
                                               .type(JsonFieldType.STRING)
                                               .description("Blog Author")
                               ),
                               responseFields(
                                       fieldWithPath("id")
                                               .type(JsonFieldType.NUMBER)
                                               .description("Blog ID"),
                                       fieldWithPath("title")
                                               .type(JsonFieldType.STRING)
                                               .description("Blog Title"),
                                       fieldWithPath("author")
                                               .type(JsonFieldType.STRING)
                                               .description("Blog Author")
                               )
                       )
               );
    }

    @Test
    public void testPutBlog() throws Exception {
        Map<String, Object> body = new HashMap<>();
        body.put("title", "new title");
        body.put("author", "new author");

        mockMvc.perform(put("/blog/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsBytes(body)))
               .andExpect(status().isOk())
               .andDo(
                       document(
                               "blog/put",
                               pathParameters(parameterWithName("id").description("Blog ID")),
                               requestFields(
                                       fieldWithPath("title")
                                               .type(JsonFieldType.STRING)
                                               .description("Blog Title"),
                                       fieldWithPath("author")
                                               .type(JsonFieldType.STRING)
                                               .description("Blog Author")
                               ),
                               responseFields(
                                       fieldWithPath("id")
                                               .type(JsonFieldType.NUMBER)
                                               .description("Blog ID"),
                                       fieldWithPath("title")
                                               .type(JsonFieldType.STRING)
                                               .description("Blog Title"),
                                       fieldWithPath("author")
                                               .type(JsonFieldType.STRING)
                                               .description("Blog Author")
                               )
                       )
               );
    }

    @Test
    public void testDeleteBlog() throws Exception {
        mockMvc.perform(delete("/blog/{id}", 1))
               .andExpect(status().isNoContent())
               .andDo(
                       document(
                               "blog/delete",
                               pathParameters(parameterWithName("id").description("Blog ID"))
                       )
               );
    }}
