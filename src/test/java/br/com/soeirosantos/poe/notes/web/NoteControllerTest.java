package br.com.soeirosantos.poe.notes.web;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.soeirosantos.poe.content.domain.entity.Content;
import br.com.soeirosantos.poe.content.web.ContentToken;
import br.com.soeirosantos.poe.notes.domain.entity.Note;
import br.com.soeirosantos.poe.notes.domain.repository.NoteRepository;
import br.com.soeirosantos.poe.security.service.UserContextService;
import br.com.soeirosantos.poe.tags.domain.entity.Tag;
import br.com.soeirosantos.poe.util.JacksonUtil;
import java.util.Collections;
import java.util.List;
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
@WebMvcTest(controllers = NoteController.class,
    secure = false,
    includeFilters = @Filter(type = FilterType.REGEX,
        pattern = "br.com.soeirosantos.poe.util.*Util"))
public class NoteControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonUtil json;

    @MockBean
    private NoteRepository noteRepository;

    @MockBean
    private UserContextService userContextService;

    @Before
    public void setup() {
        String username = "someusername";
        given(userContextService.getUsername()).willReturn(username);
        given(noteRepository.findOne(1L, username)).willReturn(getNote());
        given(noteRepository.findAll(eq(username), any()))
            .willReturn(new PageImpl<Note>(Collections.singletonList(getNote())));
    }

    @Test
    public void createNote() throws Exception {
        String noteJson = json.write(getNote());
        mvc.perform(post(NoteController.PATH)
            .content(noteJson)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isCreated());
    }

    @Test
    public void getSingleNote() throws Exception {
        mvc.perform(get(NoteController.PATH + "/{id}", 1L)
            .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.title", is("title")))
            .andExpect(jsonPath("$.content.body", is("FOO")));
    }

    @Test
    public void decrypt() throws Exception {
        mvc.perform(get(NoteController.PATH + "/{id}/decrypt", 1L)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .header(ContentToken.HEADER_NAME, "12234"))
            .andExpect(status().isOk());
    }

    @Test
    public void listNotes() throws Exception {
        mvc.perform(get(NoteController.PATH)
            .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.content", hasSize(1)))
            .andExpect(jsonPath("$.content[0].title", is("title")))
            .andExpect(jsonPath("$.content[0].content.body", is("FOO")));
    }

    @Test
    public void updateNote() throws Exception {
        String noteJson = json.write(getNote());
        mvc.perform(put(NoteController.PATH + "/{id}", 1L)
            .content(noteJson)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());
    }

    @Test
    public void deleteNote() throws Exception {
        mvc.perform(delete(NoteController.PATH + "/{id}", 1L)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());
    }

    @Test
    public void addTagForNote() throws Exception {
        String tagJson = json.write(getTags());
        mvc.perform(post(NoteController.PATH + "/{id}/tags", 1L)
            .content(tagJson)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());
    }

    @Test
    public void getTagsForNote() throws Exception {
        mvc.perform(get(NoteController.PATH + "/{id}/tags", 1L)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());
    }

    @Test
    public void removeTagsForNote() throws Exception {
        String tagJson = json.write(getTags());
        mvc.perform(delete(NoteController.PATH + "/{id}/tags", 1L)
            .content(tagJson)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());
    }

    private Note getNote() {
        Content content = new Content();
        content.setBody("FOO");
        Note note = new Note("title", content);
        return note;
    }

    private List<Tag> getTags() {
        return Collections.singletonList(new Tag("sometag"));
    }

}
