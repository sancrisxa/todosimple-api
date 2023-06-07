package com.sancrisxa.todosimple.services;

import com.sancrisxa.todosimple.models.Task;
import com.sancrisxa.todosimple.models.User;
import com.sancrisxa.todosimple.repositories.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public Task findById(Long id) {
        Optional<Task> task = this.taskRepository.findById(id);

        return task.orElseThrow(
                () -> new RuntimeException(
                        "Tarefa não encontrada! Id: " +
                        id +
                        ", Tipo: "
                        + Task.class.getName()
                )
        );
    }

    @Transactional
    public Task create(Task obj) {
        User user = this.userService.findById(obj.getUser().getId());
        obj.setId(null);

        obj.setUser(user);
        obj = this.taskRepository.save(obj);

        return obj;
    }

    @Transactional
    public Task update(Task obj) {
        Task newObj = this.findById(obj.getId());
        newObj.setDescription(obj.getDescription());

        return this.taskRepository.save(newObj);
    }

    public void delete(Long id) {
        this.findById(id);

        try {
            this.taskRepository.deleteById(id);
        } catch (Exception e){
            throw new RuntimeException("Não é possível excluir pois há entidades relacionadas!");
        }
    }
}
