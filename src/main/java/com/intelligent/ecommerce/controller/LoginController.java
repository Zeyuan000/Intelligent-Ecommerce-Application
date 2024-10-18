package com.intelligent.ecommerce.controller;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.intelligent.ecommerce.model.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private Firestore firestore;

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginRequest userInfo) {
        String username = userInfo.getUsername();
        String password = userInfo.getPassword();

        // Firestore查找比对
        try {
            QuerySnapshot querySnapshot = firestore.collection("Users").whereEqualTo("username", username).get().get();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

            if (!documents.isEmpty()) {
                String storedPassword = documents.get(0).getString("password");
                if (storedPassword != null && storedPassword.equals(password)) {
                    return ResponseEntity.ok("Login successful");
                } else {
                    return ResponseEntity.status(401).body("Wrong password");
                }
            } else {
                return ResponseEntity.status(404).body("User not found");
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server Error");
        }
    }
}
