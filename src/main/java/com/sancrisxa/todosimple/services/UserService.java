package com.sancrisxa.todosimple.services;

import com.sancrisxa.todosimple.models.User;
import com.sancrisxa.todosimple.repositories.TaskRepository;
import com.sancrisxa.todosimple.repositories.UserReposiotry;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserReposiotry userReposiotry;

    @Autowired
    private TaskRepository taskRepository;

    public User findById(Long id) {
        Optional<User> user = this.userReposiotry.findById(id);

        return user.orElseThrow(() -> new RuntimeException(
                "Usuário não encontrado! Id: " + id + ", Tipo: " + User.class.getName()

        ));
    }

    @Transactional
    public User create(User obj) {

        obj.setId(null);
        obj = this.userReposiotry.save(obj);
        this.taskRepository.saveAll(obj.getTasks());
        return obj;
    }

    @Transactional
    public User update(User obj) {
        User newObj = this.findById(obj.getId());
        newObj.setPassword(obj.getPassword());

        return this.userReposiotry.save(newObj);
    }

    public void delete(Long id) {

        this.findById(id);

        try {
            this.userReposiotry.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Não é possível excluir pois há entidades relacionadas!");
        }
    }


}
