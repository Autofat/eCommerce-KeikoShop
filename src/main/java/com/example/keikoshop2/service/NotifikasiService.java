package com.example.keikoshop2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.keikoshop2.model.Notifikasi;
import com.example.keikoshop2.repository.NotifikasiRepository;

@Service
public class NotifikasiService implements INotifikasiService {

    @Autowired
    private NotifikasiRepository notifikasiRepository;

    @Override
    public List<Notifikasi> getAllNotificationsByUserId(int userId) {
        return notifikasiRepository.findByUserId(userId);
    }

    @Override
    public Notifikasi createNotification(Notifikasi notifikasi) {
        return notifikasiRepository.save(notifikasi);
    }

    @Override
    public void markAsRead(int notificationId) {
        Optional<Notifikasi> notifikasi = notifikasiRepository.findById(notificationId);
        if (notifikasi.isPresent()) {
            Notifikasi updated = notifikasi.get();
            updated.setRead(true); // Fixed typo here
            notifikasiRepository.save(updated);
            return;
        }
        throw new RuntimeException("Notification with ID " + notificationId + " not found");
    }

    @Override
    public void deleteNotification(int notificationId) {
        if (notifikasiRepository.existsById(notificationId)) {
            notifikasiRepository.deleteById(notificationId);
        } else {
            throw new RuntimeException("Notification with ID " + notificationId + " not found");
        }
    }
}