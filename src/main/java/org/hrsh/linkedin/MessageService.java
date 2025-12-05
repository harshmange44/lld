package org.hrsh.linkedin;

import java.util.*;
import java.util.UUID;
import java.util.stream.Collectors;

public class MessageService {
    /**
     * Service for managing messages between members
     */

    public Message sendMessage(Member fromMember, Member toMember, String messageText) {
        if (fromMember == null || toMember == null || messageText == null || messageText.isEmpty()) {
            throw new IllegalArgumentException("All parameters are required");
        }

        Message message = new Message();
        message.setId(UUID.randomUUID().toString());
        message.setMessageBy(fromMember);
        message.setMessageTo(toMember);
        message.setMessageBody(new MessageBody(messageText));
        message.setMessageStatus(MessageStatus.SENT);
        message.setCreatedAt(new Date());
        message.setUpdatedAt(new Date());

        return message;
    }

    public List<Message> getConversation(String memberId1, String memberId2, Map<String, Message> messages) {
        if (memberId1 == null || memberId2 == null || messages == null) {
            return Collections.emptyList();
        }

        return messages.values().stream()
                .filter(msg -> {
                    String fromId = msg.getMessageBy().getId();
                    String toId = msg.getMessageTo().getId();
                    return (fromId.equals(memberId1) && toId.equals(memberId2)) ||
                           (fromId.equals(memberId2) && toId.equals(memberId1));
                })
                .sorted(Comparator.comparing(Message::getCreatedAt))
                .collect(Collectors.toList());
    }
}
