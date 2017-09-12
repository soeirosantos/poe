package br.com.soeirosantos.poe.tags.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.soeirosantos.poe.notes.domain.repository.NoteRepository;
import br.com.soeirosantos.poe.security.service.UserContextService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = TagController.class, secure = false)
public class TagControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private NoteRepository noteRepository;

    @MockBean
    private UserContextService userContextService;

    @Test
    public void getNotes() throws Exception {
        mvc.perform(get(TagController.PATH + TagController.NOTES_RESOURCE, "someTag"))
            .andExpect(status().isOk());
    }

}