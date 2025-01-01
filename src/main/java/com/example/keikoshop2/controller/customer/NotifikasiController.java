package com.example.keikoshop2.controller.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.keikoshop2.model.Notifikasi;
import com.example.keikoshop2.service.INotifikasiService;

@Controller
@RequestMapping("/notifications")
public class NotifikasiController {

    @Autowired
    private INotifikasiService notifikasiService;

    // Mendapatkan semua notifikasi untuk user tertentu dan menampilkan di halaman home
    @GetMapping("/user/{userId}")
    public String getNotificationsByUser(@PathVariable int userId, Model model) {
        List<Notifikasi> notifications = notifikasiService.getAllNotificationsByUserId(userId);
        model.addAttribute("notifications", notifications);
        return "home"; // Nama file HTML untuk halaman home
    }

    // Menandai notifikasi sebagai telah dibaca
    @PostMapping("/mark-as-read/{id}")
    public String markNotificationAsRead(@PathVariable int id) {
        notifikasiService.markAsRead(id);
        return "redirect:/notifications/user/{userId}"; // Redirect ke halaman notifikasi user
    }

    // Menghapus notifikasi
    @PostMapping("/delete/{id}")
    public String deleteNotification(@PathVariable int id) {
        notifikasiService.deleteNotification(id);
        return "redirect:/notifications/user/{userId}"; // Redirect ke halaman notifikasi user
    }
}
