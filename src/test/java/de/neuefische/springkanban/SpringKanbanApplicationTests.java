package de.neuefische.springkanban;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SpringKanbanApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Test
    void contextLoads() {
    }

    // Add a test for each endpoint, only checking the status code
    @Test
    void getTodos_initially_shouldReturnEmptyList() throws Exception {
        mvc.perform(get("/api/todo"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getTodos_shouldReturnListOfTodos() throws Exception {
        mvc.perform(get("/api/todo"))
                .andExpect(status().isOk());
    }

    @Test
    void getTodoById_shouldReturnTodo() throws Exception {
        mvc.perform(get("/api/todo/1"))
                .andExpect(status().isOk());
    }

    @Test
    void createTodo_shouldReturnCreated() throws Exception {
        mvc.perform(post("/api/todo")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"1\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void editTodo_shouldReturnChangedTodo() throws Exception {
        mvc.perform(put("/api/todo")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"1\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteTodo_shouldReturnNoContent() throws Exception {
        mvc.perform(delete("/api/todos/1"))
                .andExpect(status().isNoContent());
    }
}
