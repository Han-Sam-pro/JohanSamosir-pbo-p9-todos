package org.delcom.app.service; // Package untuk kelas tes

// Import yang dibutuhkan
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.delcom.app.entities.CashFlow;
import org.delcom.app.repositories.ICashFlowRepository;
import org.delcom.app.services.ICashFlowService; // <-- INI SOLUSINYA
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TodoServiceTest {
    @Test
    @DisplayName("Pengujian untuk service Todo")
    void testTodoService() throws Exception {
        // Buat random UUID
        UUID todoId = UUID.randomUUID();
        UUID nonexistentTodoId = UUID.randomUUID();

        // Membuat dummy data
        CashFlow todo = new CashFlow("Belajar Spring Boot", "Belajar mock repository di unit test", false);
        todo.setId(todoId);

        // Membuat mock TodoRepository
        ICashFlowRepository todoRepository = Mockito.mock(ICashFlowRepository.class);

        // Atur perilaku mock
        when(todoRepository.save(any(CashFlow.class))).thenReturn(todo);
        when(todoRepository.findByKeyword("Belajar")).thenReturn(java.util.List.of(todo));
        when(todoRepository.findAll()).thenReturn(java.util.List.of(todo));
        when(todoRepository.findById(todoId)).thenReturn(java.util.Optional.of(todo));
        when(todoRepository.findById(nonexistentTodoId)).thenReturn(java.util.Optional.empty());
        when(todoRepository.existsById(todoId)).thenReturn(true);
        when(todoRepository.existsById(nonexistentTodoId)).thenReturn(false);
        doNothing().when(todoRepository).deleteById(any(UUID.class));

        // Membuat instance service
        ICashFlowService todoService = new ICashFlowService(todoRepository); // Baris ini tidak akan error lagi
        assert (todoService != null);

        // Menguji create todo
        {
            CashFlow createdTodo = todoService.createTodo(todo.getTitle(), todo.getDescription());
            assert (createdTodo != null);
            assert (createdTodo.getId().equals(todoId));
            assert (createdTodo.getTitle().equals(todo.getTitle()));
            assert (createdTodo.getDescription().equals(todo.getDescription()));
        }

        // Menguji getAllTodos
        {
            List<CashFlow> todos = todoService.getAllTodos(null);
            assert (todos.size() == 1);
        }

        // Menguji getAllTodos dengan pencarian
        {
            List<CashFlow> todos = todoService.getAllTodos("Belajar");
            assert (todos.size() == 1);

            todos = todoService.getAllTodos("     ");
            assert (todos.size() == 1);
        }

        // ... sisa kode pengujian Anda tetap sama ...
        
        // Menguji getTodoById
        {
            CashFlow fetchedTodo = todoService.getTodoById(todoId);
            assert (fetchedTodo != null);
            assert (fetchedTodo.getId().equals(todoId));
            assert (fetchedTodo.getTitle().equals(todo.getTitle()));
            assert (fetchedTodo.getDescription().equals(todo.getDescription()));
        }

        // Menguji getTodoById dengan ID yang tidak ada
        {
            CashFlow fetchedTodo = todoService.getTodoById(nonexistentTodoId);
            assert (fetchedTodo == null);
        }

        // Menguji updateTodo
        {
            String updatedTitle = "Belajar Spring Boot Lanjutan";
            String updatedDescription = "Belajar mock repository di unit test dengan Mockito";
            Boolean updatedIsFinished = true;

            CashFlow updatedTodo = todoService.updateTodo(todoId, updatedTitle, updatedDescription, updatedIsFinished);
            assert (updatedTodo != null);
            assert (updatedTodo.getTitle().equals(updatedTitle));
            assert (updatedTodo.getDescription().equals(updatedDescription));
            assert (updatedTodo.isFinished() == updatedIsFinished);
        }

        // Menguji update Todo dengan ID yang tidak ada
        {
            String updatedTitle = "Belajar Spring Boot Lanjutan";
            String updatedDescription = "Belajar mock repository di unit test dengan Mockito";
            Boolean updatedIsFinished = true;

            CashFlow updatedTodo = todoService.updateTodo(nonexistentTodoId, updatedTitle, updatedDescription,
                    updatedIsFinished);
            assert (updatedTodo == null);
        }

        // Menguji deleteTodo
        {
            boolean deleted = todoService.deleteTodo(todoId);
            assert (deleted == true);
        }

        // Menguji deleteTodo dengan ID yang tidak ada
        {
            boolean deleted = todoService.deleteTodo(nonexistentTodoId);
            assert (deleted == false);
        }
    }
}