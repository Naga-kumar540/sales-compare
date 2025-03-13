package com.example.salespurchase.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.example.salespurchase.service.NotificationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class NotificationInterceptor implements HandlerInterceptor {

    @Autowired
    private NotificationService notificationService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        if (modelAndView != null) {
            // Add unread notification count to all views
            try {
                Long unreadCount = notificationService.countUnreadNotifications();
                modelAndView.addObject("unreadCount", unreadCount);
            } catch (Exception e) {
                // In case notification service is not yet initialized (e.g., during application startup)
                modelAndView.addObject("unreadCount", 0L);
            }
        }
    }
}