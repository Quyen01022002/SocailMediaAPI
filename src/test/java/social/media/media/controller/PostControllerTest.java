package social.media.media.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import social.media.media.model.entity.Post;
import social.media.media.model.enums.StatusEnum;
import social.media.media.model.reponse.PostResponse;
import social.media.media.model.reponse.postImgResponse;
import social.media.media.model.request.PostRequest;
import social.media.media.service.PostService;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PostService postService;
    private PostRequest postRequest;
    private PostResponse postResponse;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void initData() {
        List<postImgResponse> posts = new ArrayList<>();
        postImgResponse postImgResponse = new postImgResponse();
        postImgResponse.setLinkPicture("anh 1");
        posts.add(postImgResponse);
        postImgResponse postImgResponse2 = new postImgResponse();
        postImgResponse.setLinkPicture("anh 2");
        posts.add(postImgResponse2);
        Mockito.when(postService.addPost(ArgumentMatchers.any(),ArgumentMatchers.anyList())).thenReturn(postResponse);
        postRequest = PostRequest.builder()
                .classes(1)
                .contentPost("Content")
                .groups(1)
                .status(true)
                .listAnh(posts)
                .build();
        postResponse = PostResponse.builder()
                .contentPost("Content")
                .groupid(1)
                .status(true)
                .listAnh(posts)
                .build();
    }

    @Test
    void createPost() throws Exception {
        //Given
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(postRequest);

        // When
        mockMvc.perform(MockMvcRequestBuilders
                .post("/post")
                .contentType(MediaType.APPLICATION_ATOM_XML_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("status").value(StatusEnum.SUCCESS)
                );

        // Then


    }
}
