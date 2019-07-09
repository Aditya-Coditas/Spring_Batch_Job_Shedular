package com.coditas.SpringBatchSchedular.config;

import com.coditas.SpringBatchSchedular.model.User;
import com.coditas.SpringBatchSchedular.repository.UserRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Writer implements ItemWriter<User> {

    @Autowired
    UserRepository repo;

    @Override
    public void write(List<? extends User> list) throws Exception {
        for(User u:list)
            repo.save(u);
    }
}
