import {Todo} from "../types/Todo.tsx";
import './TodoCard.css';

export function TodoCardComponent({ todo }: { todo: Todo }) {
    return (
        <div className="todo-card">
            <h2>{todo.description}</h2>
            <p>{todo.status}</p>
        </div>
    );
}