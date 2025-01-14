import React, { useState } from 'react';
import axios from 'axios';
import './AddTodo.css';

type Todo = {
    id: string;
    description: string;
    status: 'OPEN' | 'IN_PROGRESS' | 'DONE';
};

type AddTodoFormProps = {
    onTodoAdded: (todo: Todo) => void;
};

function AddTodoForm({ onTodoAdded }: AddTodoFormProps) {
    const [description, setDescription] = useState('');
    const [status, setStatus] = useState<'OPEN' | 'IN_PROGRESS' | 'DONE'>('OPEN');

    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault();
        const newTodo = { id: '', description, status };
        const response = await axios.post('/api/todo', newTodo);
        onTodoAdded(response.data);
        setDescription('');
        setStatus('OPEN');
    };

    return (
        <div className="add-todo-form">
            <form onSubmit={handleSubmit}>
                <input
                    type="text"
                    placeholder="Description"
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                    required
                />
                <select
                    value={status}
                    onChange={(e) => setStatus(e.target.value as 'OPEN' | 'IN_PROGRESS' | 'DONE')}
                    required
                >
                    <option value="OPEN">OPEN</option>
                    <option value="IN_PROGRESS">IN_PROGRESS</option>
                    <option value="DONE">DONE</option>
                </select>
                <button type="submit">➕</button>
            </form>
        </div>
    );
}

export default AddTodoForm;
