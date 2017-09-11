package br.com.soeirosantos.poe.bookmarks.web;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.soeirosantos.poe.bookmarks.domain.entity.Bookmark;
import br.com.soeirosantos.poe.bookmarks.domain.repository.BookmarkRepository;
import br.com.soeirosantos.poe.content.domain.entity.Content;
import br.com.soeirosantos.poe.content.web.ContentToken;
import br.com.soeirosantos.poe.security.service.UserContextService;
import br.com.soeirosantos.poe.util.JacksonUtil;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = BookmarkController.class,
    secure = false,
    includeFilters = @Filter(type = FilterType.REGEX,
        pattern = "br.com.soeirosantos.poe.util.*Util"))
public class BookmarkControllerTest {


    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonUtil json;

    @MockBean
    private BookmarkRepository bookmarkRepository;

    @MockBean
    private UserContextService userContextService;

    @Before
    public void setup() {
        String username = "someusername";
        given(userContextService.getUsername()).willReturn(username);
        given(bookmarkRepository.findOne(1L, username)).willReturn(getBookmark());
        given(bookmarkRepository.findAll(eq(username), any()))
            .willReturn(new PageImpl<Bookmark>(Collections.singletonList(getBookmark())));
    }

    @Test
    public void createNote() throws Exception {
        String noteJson = json.write(getBookmark());
        mvc.perform(post(BookmarkController.PATH)
            .content(noteJson)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isCreated());
    }

    @Test
    public void getSingleBookmark() throws Exception {
        mvc.perform(get(BookmarkController.PATH + "/{id}", 1L)
            .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.name", is("name")))
            .andExpect(jsonPath("$.content.body", is("FOO")));
    }

    @Test
    public void decrypt() throws Exception {
        mvc.perform(get(BookmarkController.PATH + "/{id}/decrypt", 1L)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .header(ContentToken.HEADER_NAME, "12234"))
            .andExpect(status().isOk());
    }

    @Test
    public void listBookmarks() throws Exception {
        mvc.perform(get(BookmarkController.PATH)
            .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.content", hasSize(1)))
            .andExpect(jsonPath("$.content[0].name", is("name")))
            .andExpect(jsonPath("$.content[0].content.body", is("FOO")));
    }

    @Test
    public void updateBookmark() throws Exception {
        String bookmarkJson = json.write(getBookmark());
        mvc.perform(put(BookmarkController.PATH + "/{id}", 1L)
            .content(bookmarkJson)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());
    }

    @Test
    public void deleteBookmark() throws Exception {
        mvc.perform(delete(BookmarkController.PATH + "/{id}", 1L)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());
    }

    @Test
    public void addTag() throws Exception {
        //TODO
    }

    @Test
    public void getTags() throws Exception {
        //TODO
    }

    @Test
    public void removeTags() throws Exception {
        //TODO
    }

    private Bookmark getBookmark() {
        Content content = new Content();
        content.setBody("FOO");
        Bookmark bookmark = new Bookmark("name", content);
        return bookmark;
    }


}