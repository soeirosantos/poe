package br.com.soeirosantos.poe.security.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.soeirosantos.poe.security.service.RefreshJwtService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = RefreshTokenResource.class, secure = false)
public class RefreshTokenResourceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RefreshJwtService refreshJwtService;

    @Test
    public void refreshToken() throws Exception {
        mvc.perform(get(RefreshTokenResource.PATH)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());
    }

}