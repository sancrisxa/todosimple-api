package com.sancrisxa.todosimple.repositories;

import com.sancrisxa.todosimple.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UaerReposiotry  extends JpaRepository<User, Long> {
}
