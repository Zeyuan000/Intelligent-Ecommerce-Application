package com.intelligent.ecommerce.controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
public class RegisterController {

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Map<String, String> userInfo) {
        String username = userInfo.get("username");
        String password = userInfo.get("password");

        Firestore db = FirestoreClient.getFirestore();
        CollectionReference usersCollection = db.collection("Users");

        // 检查是否已存在相同用户名的用户
        try {
            if (db.collection("users").document(username).get().get().exists()) {
                return ResponseEntity.status(409).body("Fail:Username already exists");
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server Error");
        }

        // 创建新用户数据
        Map<String, String> newUser = new HashMap<>();
        newUser.put("username", username);
        newUser.put("password", password);
        // 将用户数据存入 Firestore
        ApiFuture<com.google.cloud.firestore.WriteResult> result = usersCollection.document(username).set(newUser);

        try {
            // 等待存储结果
            result.get();
            return ResponseEntity.ok("Successful registration");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server Error");
        }
    }
}

