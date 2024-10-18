package com.intelligent.ecommerce.controller;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
public class LoginController {

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody Map<String, String> userInfo) {
        String username = userInfo.get("username");
        String password = userInfo.get("password");

        Firestore db = FirestoreClient.getFirestore();

        try {
            // 获取Firestore中存储的用户数据
            DocumentSnapshot document = db.collection("Users").document(username).get().get();

            if (document.exists()) {
                String storedPassword = document.getString("password");

                // 检查密码是否匹配
                if (storedPassword != null && storedPassword.equals(password)) {
                    return ResponseEntity.ok("Success");
                } else {
                    return ResponseEntity.status(401).body("Fail:Incorrect password");
                }
            } else {
                return ResponseEntity.status(404).body("Fail:User does not exist");
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server Error");
        }
    }
}
