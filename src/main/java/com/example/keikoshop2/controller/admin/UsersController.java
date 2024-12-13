package com.example.keikoshop2.controller.admin;

import com.example.keikoshop2.model.User;
import com.example.keikoshop2.service.IUserService;
import org.springframework.stereotype.Controller;

import com.example.keikoshop2.model.Product;
import com.example.keikoshop2.service.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Controller
@PreAuthorize("hasRole('admin')")
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {
	private final IUserService userService;
	private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

	@GetMapping("/manage-users")
	public String manageUsers(Model model) {
		List<User> users = userService.getAllUsers();
		model.addAttribute("users", users);
		return "admin/users";
	}

	@PostMapping("/store")
	public String createUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
		try {
			userService.storeUser(user);
			redirectAttributes.addFlashAttribute("successMessage", "User successfully added");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
		}
		return "redirect:/users/manage-users";
	}

	@PutMapping("/update/{id}")
	public String updateUser(@PathVariable("id") int id, @ModelAttribute User user,
			RedirectAttributes redirectAttributes) {
		try {
			userService.updateUser(user, id);
			redirectAttributes.addFlashAttribute("successMessage", "User successfully updated");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
		}
		return "redirect:/users/manage-users";
	}

	@DeleteMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
		try {
			userService.deleteUser(id);
			redirectAttributes.addFlashAttribute("successMessage", "User successfully deleted");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
		}
		return "redirect:/users/manage-users";
	}

	@GetMapping("/getUsers/{id}")
	@ResponseBody
	public User getUsersById(@PathVariable("id") int id) {
		return userService.getUserById(id);
	}

}
