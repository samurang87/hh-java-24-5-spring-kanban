import axios from 'axios';
import {Todo} from '../types/Todo';
import {TodoCardComponent} from './TodoCard';
import './TodoColumn.css';

export async function fetchTodos(): Promise<Todo[]> {
    const response = await axios.get('/api/todo');
    return response.data;
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
