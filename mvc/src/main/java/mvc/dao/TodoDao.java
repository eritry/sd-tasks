package mvc.dao;

import mvc.model.Todo;
import mvc.model.TodoList;

import java.util.List;

public interface TodoDao {
    int addTodoList(TodoList todoList);

    void removeTodoList(int id);

    List<TodoList> getTodoLists();

    int addTodo(int listId, Todo todo);

    void changeStatus(int todoId);
}
