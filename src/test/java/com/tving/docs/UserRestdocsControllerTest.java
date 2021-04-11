package com.tving.docs;

import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.ResourceSnippetParametersBuilder;
import com.epages.restdocs.apispec.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(UserRestdocsController.class)
@ExtendWith(RestDocumentationExtension.class)
class UserRestdocsControllerTest {

  @Autowired
  private ObjectMapper om;

  private MockMvc mockMvc;

  @BeforeEach
  public void setup(
      WebApplicationContext webApplicationContext,
      RestDocumentationContextProvider restDocumentationContextProvider) {
    this.mockMvc =
        MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentationContextProvider))
            .build();
  }

  @Test
  void testGet() throws Exception {

    this.mockMvc
        .perform(
            get("/user/restdocs/{id}", "001")
                .accept(MediaType.APPLICATION_JSON)
                .param("method", "Method.GET"))
        .andDo(print())
        .andDo(
            document(
                "{class-name}/{method-name}",
                resource(
                    builder()
                        .responseSchema(Schema.schema("Restdocs"))
                        .summary("User Method.GET")
                        .description("method.GET description")
                        .pathParameters(parameterWithName("id").description("User ID"))
                        .requestParameters(
                            parameterWithName("method").optional().description("HTTP Method"))
                        .responseFields(
                            fieldWithPath("id").type(JsonFieldType.STRING).description("User ID"),
                            fieldWithPath("name").type(JsonFieldType.STRING).description("User Name"),
                            fieldWithPath("email").type(JsonFieldType.STRING).optional().description("Email"),
                            fieldWithPath("method")
                                .type(JsonFieldType.STRING)
                                .optional()
                                .description("Http Method"))
                        .build())));
  }

  @Test
  void testPost() throws Exception {
    RestdocsForm form = new RestdocsForm();
    form.setId("002");
    form.setName("choonghyun");
    form.setEmail("choonghyun83@gmail.com");
    form.setMethod("Method.Post");
    String content = om.writeValueAsString(
        form
    );

    this.mockMvc.perform(post("/user/restdocs").contentType(MediaType.APPLICATION_JSON)
    .content(content).accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andDo(document("{class-name}/{method-name}",resource(
            builder()
                .requestSchema(Schema.schema("RestdocsForm"))
                .requestFields(
                    fieldWithPath("id").type(JsonFieldType.STRING).description("ID"),
                    fieldWithPath("name").type(JsonFieldType.STRING).description("User Name"),
                    fieldWithPath("email").type(JsonFieldType.STRING).optional().description("Email"),
                    fieldWithPath("method").type(JsonFieldType.STRING).optional().description("Http Method")
                )
                .build()
        )));
  }

  private ResourceSnippetParametersBuilder builder() {
    return ResourceSnippetParameters.builder().tags("restdocs-user");
  }
}
