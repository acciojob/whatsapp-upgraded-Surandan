package com.driver;

import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class WhatsappRepository {
    static int groupCount = 0;
    static int msgCount = 0;
    Map<String,User> userMap = new HashMap<>();
    Map<String,Group> groupMap = new HashMap<>();   // grpName : Group
    Map<Integer,Group> msgGroup = new HashMap<>();   // msgId : group
    Map<Integer,Message> mesageMap = new HashMap<>();  // msgId : Message
//    Map<Group,String> adminMap = new HashMap<>();    // Group : adminMobile

    public String createUser(String name, String mobile) {
          if(userMap.containsKey(mobile)) return "Fail";
          User user = new User(name,mobile,0);
          userMap.put(mobile,user);
          return "Success";
    }

    public Group createGroup(List<User> users) {
        Group group = new Group();
        if(users.size() == 2) {
            group.setName(users.get(1).getName());
            group.setNumberOfParticipants(2);
        }
        else {
            group.setName("Group "+groupCount);
            group.setNumberOfParticipants(users.size());
            groupCount++;
        }

        group.setAdmin(users.get(0));
        group.setMsgCnt(0);
        group.setUsers(users);
        groupMap.put(group.getName(),group);
        return group;
    }

    public int createMessage(String content) {
        Message message = new Message();
        msgCount++;
        message.setContent(content);
        message.setId(msgCount);
        message.setSender(null);
        Date date = new Date();
        message.setTimestamp(date);
        mesageMap.put(msgCount,message);
        return msgCount;
    }

    public int sendMessage(Message message, User sender, Group group) throws Exception {
        if (!groupMap.containsKey(group.getName())) throw new Exception("Group does not exist");
        List<User> users =group.getUsers();
        if(!users.contains(sender)) throw new Exception("You are not allowed to send message");
        message.setSender(sender);
        msgGroup.put(message.getId(),group);
        group.setMsgCnt(group.getMsgCnt()+1);
        return group.getMsgCnt();
    }

    public String changeAdmin(User approver, User user, Group group) throws Exception {
        if (!groupMap.containsKey(group.getName())) throw new Exception("Group does not exist");
        if(group.getAdmin()!=approver) throw new Exception("Approver does not have rights");
        List<User> users =group.getUsers();
        if(!users.contains(user)) throw new Exception("User is not a participant");
        group.setAdmin(user);
        return "SUCCESS";
    }


    public int removeUser(User user) throws Exception {
        boolean flag = false;
        for (String groupName : groupMap.keySet()) {
            List<User> userList = groupMap.get(groupName).getUsers();
            if (!userList.contains(user)) continue;
            flag = true;
            if (groupMap.get(groupName).getAdmin() == user) throw new Exception("Cannot remove admin");

            Group group = groupMap.get(groupName);
            group.setMsgCnt(group.getMsgCnt()-user.getMsgSent());
            msgCount-=user.getMsgSent();
            userList.remove(user);
            group.setUsers(userList);
            groupMap.put(groupName,group);
            userMap.remove(user.getMobile());

            for (int msgId : mesageMap.keySet()) {
                if(mesageMap.get(msgId).getSender() == user) {
                    mesageMap.remove(msgId);
                    msgGroup.remove(msgId);
                }
            }

            return userMap.size()+group.getMsgCnt()+msgCount;
        }
        if (!flag) throw new Exception("User not found");
        return 0;
    }

    public String findMessage(Date start, Date end, int k) {
        return "";
    }
}
