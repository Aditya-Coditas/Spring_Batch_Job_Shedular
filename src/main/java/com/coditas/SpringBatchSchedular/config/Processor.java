package com.coditas.SpringBatchSchedular.config;

import com.coditas.SpringBatchSchedular.model.User;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class Processor implements ItemProcessor<User, User> {
    @Override
    public User process(User user) throws Exception {
        User u=new User();
        u.setId(user.getId());
        u.setName(user.getName().toUpperCase());
        u.setSal(user.getSal()+1000);
        return u;
    }
}
