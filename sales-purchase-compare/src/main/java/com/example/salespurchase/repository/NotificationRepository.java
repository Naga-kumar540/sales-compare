package com.example.salespurchase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.salespurchase.model.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByNotificationType(String notificationType);
    
    List<Notification> findByIsRead(Boolean isRead);
    
    @Query("SELECT COUNT(n) FROM Notification n WHERE n.isRead = false")
    Long countUnreadNotifications();
    
    List<Notification> findByItemNameAndDomainName(String itemName, String domainName);
}