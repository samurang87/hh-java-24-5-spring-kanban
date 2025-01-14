import {Status, Todo} from '../types/Todo';
import {TodoCardComponent} from './TodoCard';
import todos from '../../public/payloadListTodo.json';
import './TodoColumn.css';

export function fetchTodos(): Todo[] {
    return todos.map(todo => {
        return {
            id: todo.id,
            description: todo.description,
            status: todo.status as Status
        };
    });
}

type TodoColumnProps = {
    todoList: Todo[];
};

function TodoColumn({ todoList }: TodoColumnProps) {

    return (
        <div className="todo-column">
            {todoList.map(todo => (
                <TodoCardComponent key={todo.id} todo={todo}/>
            ))}
        </div>
    );
}

export default TodoColumn;
