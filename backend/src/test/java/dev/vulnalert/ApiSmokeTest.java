package dev.vulnalert;
import org.junit.jupiter.api.Test; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc; import org.springframework.boot.test.context.SpringBootTest; import org.springframework.test.web.servlet.MockMvc; import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get; import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest @AutoConfigureMockMvc
class ApiSmokeTest { @Autowired MockMvc mvc; @Test void dashboardCreatesDemoUser()throws Exception{mvc.perform(get("/api/dashboard").header("X-Demo-User","test@example.com")).andExpect(status().isOk()).andExpect(jsonPath("$.watchedProducts").value(0));} }
