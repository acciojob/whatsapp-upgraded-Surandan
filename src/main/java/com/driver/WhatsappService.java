package com.driver;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WhatsappService {
    WhatsappRepository whatsappRepository = new WhatsappRepository();

    public String createUser(String name, String mobile) {
        whatsappRepository.createUser(name,mobile);
    }

    public Group createGroup(List<User> users) {
        whatsappRepository.createGroup(users);
    }

    public int createMessage(String content) {
        whatsappRepository.createMessage(content);
    }

    public int sendMessage(Message message, User sender, Group group) {
        whatsappRepository.sendMessage(message,sender,group);
    }

    public String changeAdmin(User approver, User user, Group group) {
        whatsappRepository.changeAdmin(approver,user,group);
    }

    public int removeUser(User user) {
        whatsappRepository.removeUser(user);
    }

    public String findMessage(Date start, Date end, int k) {
        whatsappRepository.findMessage(start,end,k);
    }
}
