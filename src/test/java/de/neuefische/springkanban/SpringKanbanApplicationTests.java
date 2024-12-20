package de.neuefische.springkanban;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class SpringKanbanApplicationTests {

    @Autowired
    private TodoRepository todoRepo;

    @Autowired
    private MockMvc mvc;

    @Test
    void contextLoads() {
    }

    @Test
    void getTodos_initially_shouldReturnEmptyList() throws Exception {
        mvc.perform(get("/api/todo"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getTodos_shouldReturnListOfTodos() throws Exception {

        Todo todo1 = new Todo(
                "1",
                "Brush teeth",
                Status.OPEN
        );
        Todo todo2 = new Todo(
                "2",
                "Do laundry",
                Status.DONE
        );

        todoRepo.insert(todo1);
        todoRepo.insert(todo2);

        mvc.perform(get("/api/todo"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":\"1\",\"description\":\"Brush teeth\",\"status\":\"OPEN\"},{\"id\":\"2\",\"description\":\"Do laundry\",\"status\":\"DONE\"}]"));
    }

    @Test
    void getTodosByStatus_shouldReturnListOfTodos() throws Exception {
        Todo todo1 = new Todo(
                "1",
                "Brush teeth",
                Status.OPEN
        );
        Todo todo2 = new Todo(
                "2",
                "Do laundry",
                Status.DONE
        );

        todoRepo.insert(todo1);
        todoRepo.insert(todo2);
        mvc.perform(get("/api/todo?status=OPEN"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":\"1\",\"description\":\"Brush teeth\",\"status\":\"OPEN\"}]"));
    }

    @Test
    void getTodoById_shouldReturnTodo() throws Exception {

        Todo todo = new Todo(
                "2",
                "Test task",
                Status.OPEN
        );
        todoRepo.insert(todo);

        mvc.perform(get("/api/todo/2"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":\"2\",\"description\":\"Test task\",\"status\":\"OPEN\"}"));
    }

    @Test
    void getTodoById_shouldReturnBadRequest() throws Exception {
        mvc.perform(get("/api/todo/2"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("An error occurred \uD83D\uDE40 No value present"))
                .andExpect(jsonPath("$.timestamp").value(org.hamcrest.Matchers.matchesPattern("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.*")));
    }

    @Test
    void createTodo_shouldReturnCreated() throws Exception {
        MvcResult res = mvc.perform(post("/api/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"Some description\", \"status\":\"OPEN\"}"))
                .andExpect(status().isCreated())
                .andReturn();

        Todo resTodo = todoRepo.findById(res.getResponse().getContentAsString()).orElseThrow();

        Assertions.assertEquals("Some description", resTodo.description());
        Assertions.assertEquals(Status.OPEN, resTodo.status());
        Assertions.assertFalse(resTodo.id().isEmpty(), "ID should not be empty");
    }

    @Test
    void editTodo_shouldReturnChangedTodo() throws Exception {

        Todo todo1 = new Todo(
                "1",
                "Brush teeth",
                Status.OPEN
        );
        todoRepo.insert(todo1);
        mvc.perform(put("/api/todo/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"Brush teeth and floss\", \"status\":\"DONE\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":\"1\",\"description\":\"Brush teeth and floss\",\"status\":\"DONE\"}"));
    }

    @Test
    void deleteTodo_shouldReturnNoContent() throws Exception {
        Todo todo1 = new Todo(
                "1",
                "Brush teeth",
                Status.OPEN
        );
        todoRepo.insert(todo1);

        mvc.perform(delete("/api/todo/1"))
                .andExpect(status().isNoContent());

        Assertions.assertTrue(todoRepo.findById("1").isEmpty());
    }
}
