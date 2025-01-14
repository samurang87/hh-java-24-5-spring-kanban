import './App.css'
import TodoColumn, {fetchTodos} from "./components/TodoColumn.tsx";
import {useEffect, useState} from "react";
import {Todo} from "./types/Todo.tsx";

function App() {

    const [todoList, setTodoList] = useState<Todo[]>([]);

    useEffect(() => {
        const todos = fetchTodos();
        setTodoList(todos);
    }, []);

    const openTodos = todoList.filter(todo => todo.status === 'OPEN');
    const inProgressTodos = todoList.filter(todo => todo.status === 'IN_PROGRESS');
    const doneTodos = todoList.filter(todo => todo.status === 'DONE');

  return (
    <>
      <h1>My to-do list</h1>
        <div style={{display: 'flex', justifyContent: 'space-between'}}>
            <TodoColumn todoList={openTodos}/>
            <TodoColumn todoList={inProgressTodos}/>
            <TodoColumn todoList={doneTodos}/>
        </div>
    </>
  )
}

export default App
