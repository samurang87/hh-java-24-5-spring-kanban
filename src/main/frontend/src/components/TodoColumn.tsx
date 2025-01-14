import {Status, Todo} from '../types/Todo';
import {TodoCardComponent} from './TodoCard';
import todos from '../../public/payloadListTodo.json';
import {useEffect, useState} from "react";

function fetchTodos(): Todo[] {
    return todos.map(todo => {
        return {
            id: todo.id,
            description: todo.description,
            status: todo.status as Status
        };
    });
}

function TodoColumn() {
    const [todoList, setTodoList] = useState<Todo[]>([]);

    useEffect(() => {
        const todos = fetchTodos();
        setTodoList(todos);
    }, []);

    return (
        <div>
            {todoList.map(todo => (
                <TodoCardComponent key={todo.id} todo={todo}/>
            ))}
        </div>
    );
}

export default TodoColumn;
