package dev.vulnalert;
import org.junit.jupiter.api.Test; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc; import org.springframework.boot.test.context.SpringBootTest; import org.springframework.http.MediaType; import org.springframework.test.web.servlet.MockMvc; import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest @AutoConfigureMockMvc
class ApiControllerTest {
 @Autowired MockMvc mvc;
 @Test void createsAndListsWatchItem()throws Exception{mvc.perform(post("/api/watchlist").contentType(MediaType.APPLICATION_JSON).content("{\"vendor\":\"Apache\",\"product\":\"Log4j\",\"version\":\"2.17\"}")).andExpect(status().isCreated()).andExpect(jsonPath("$.product").value("Log4j"));mvc.perform(get("/api/watchlist")).andExpect(status().isOk()).andExpect(jsonPath("$[0].vendor").value("Apache"));}
}
