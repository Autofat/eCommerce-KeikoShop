package com.example.keikoshop2.service;

import java.util.List;

import com.example.keikoshop2.model.Notifikasi;

public interface INotifikasiService {
    List<Notifikasi> getAllNotificationsByUserId(int userId);
    Notifikasi createNotification(Notifikasi notifikasi);
    void markAsRead(int notificationId);
    void deleteNotification(int notificationId);
}
