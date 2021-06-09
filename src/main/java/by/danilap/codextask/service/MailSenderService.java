package by.danilap.codextask.service;

import by.danilap.codextask.dto.item.ItemDTO;
import by.danilap.codextask.entity.User;

public interface MailSenderService {

    void sendPurchaseMessage(User to);

    void sendItemUpdateMessage(User to, ItemDTO itemBeforeUpdate, ItemDTO itemAfterUpdate);
}
