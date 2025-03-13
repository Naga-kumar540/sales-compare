package com.example.salespurchase.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.salespurchase.model.Notification;
import com.example.salespurchase.service.NotificationService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/notification")
//@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    
   
	public NotificationController(NotificationService notificationService) {
		super();
		this.notificationService = notificationService;
	}

	@GetMapping("/list")
    public String listNotifications(Model model) {
        List<Notification> notifications = notificationService.findAll();
        model.addAttribute("notifications", notifications);
        model.addAttribute("unreadCount", notificationService.countUnreadNotifications());
        return "notification/list";
    }
    
    @GetMapping("/unread")
    public String listUnreadNotifications(Model model) {
        List<Notification> unreadNotifications = notificationService.findByIsRead(false);
        model.addAttribute("notifications", unreadNotifications);
        model.addAttribute("unreadCount", unreadNotifications.size());
        return "notification/list";
    }
    
    @GetMapping("/read/{id}")
    public ResponseEntity<String> markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok("Notification marked as read");
    }
    
    @GetMapping("/read-all")
    public String markAllAsRead() {
        notificationService.markAllAsRead();
        return "redirect:/notification/list";
    }
    
    @GetMapping("/type/{type}")
    public String filterByType(@PathVariable String type, Model model) {
        List<Notification> filteredNotifications = notificationService.findByNotificationType(type);
        model.addAttribute("notifications", filteredNotifications);
        model.addAttribute("unreadCount", notificationService.countUnreadNotifications());
        model.addAttribute("activeFilter", type);
        return "notification/list";
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable Long id) {
        notificationService.deleteById(id);
        return ResponseEntity.ok("Notification deleted");
    }
    
    @GetMapping("/count")
    @ResponseBody
    public ResponseEntity<Long> getUnreadCount() {
        return ResponseEntity.ok(notificationService.countUnreadNotifications());
    }
}