package com.example.salespurchase.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.salespurchase.model.Notification;
import com.example.salespurchase.repository.NotificationRepository;

@Service
//@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    public NotificationService(NotificationRepository notificationRepository) {
    	this.notificationRepository = notificationRepository;
    }
    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    public Optional<Notification> findById(Long id) {
        return notificationRepository.findById(id);
    }

    @Transactional
    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Transactional
    public void deleteById(Long id) {
        notificationRepository.deleteById(id);
    }

    public List<Notification> findByNotificationType(String notificationType) {
        return notificationRepository.findByNotificationType(notificationType);
    }

    public List<Notification> findByIsRead(Boolean isRead) {
        return notificationRepository.findByIsRead(isRead);
    }

    public Long countUnreadNotifications() {
        return notificationRepository.countUnreadNotifications();
    }

    @Transactional
    public void markAsRead(Long id) {
        Optional<Notification> notification = notificationRepository.findById(id);
        notification.ifPresent(n -> {
            n.setIsRead(true);
            notificationRepository.save(n);
        });
    }

    @Transactional
    public void markAllAsRead() {
        List<Notification> unreadNotifications = notificationRepository.findByIsRead(false);
        unreadNotifications.forEach(notification -> notification.setIsRead(true));
        notificationRepository.saveAll(unreadNotifications);
    }
    
    public List<Notification> findByItemNameAndDomainName(String itemName, String domainName) {
        return notificationRepository.findByItemNameAndDomainName(itemName, domainName);
    }
}