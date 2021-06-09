package by.danilap.codextask.service.impl;

import by.danilap.codextask.dto.item.ItemDTO;
import by.danilap.codextask.dto.item.ItemRequestDTO;
import by.danilap.codextask.entity.User;
import by.danilap.codextask.mapper.ItemMapper;
import by.danilap.codextask.repository.TagRepository;
import by.danilap.codextask.service.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSenderServiceImpl implements MailSenderService {

    private final MailSender mailSender;
    private final ItemMapper itemMapper;

    @Override
    public void sendPurchaseMessage(User to) {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("Your order:").append("\n");
        to.getUserItems().forEach(item -> {
            messageBuilder.append(itemMapper.convertToDTO(item)).append("\n");
        });

        sendMessage(to, "Your order details", messageBuilder.toString());
    }

    @Override
    public void sendItemUpdateMessage(User to, ItemDTO itemBeforeUpdate, ItemDTO itemAfterUpdate) {
        StringBuilder messageBuilder = new StringBuilder();

        messageBuilder.append("Item before update:").append("\n");
        messageBuilder.append("Name: ").append(itemBeforeUpdate.getName()).append("\n");
        messageBuilder.append("Description: ").append(itemBeforeUpdate.getDescription()).append("\n");
        messageBuilder.append("Tags: ").append(itemBeforeUpdate.getTags()).append("\n");

        messageBuilder.append("************************").append("\n");

        messageBuilder.append("Item after update:").append("\n");
        messageBuilder.append("Name: ").append(itemAfterUpdate.getName()).append("\n");
        messageBuilder.append("Description: ").append(itemAfterUpdate.getDescription()).append("\n");
        messageBuilder.append("Tags: ").append(itemAfterUpdate.getTags()).append("\n");

        sendMessage(to, "Item from your cart was updated", messageBuilder.toString());
    }

    private void sendMessage(User to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("shushambin@gmail,com");
        message.setTo(to.getEmail());
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }
}
