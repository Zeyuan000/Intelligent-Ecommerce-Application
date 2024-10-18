package com.intelligent.ecommerce.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp initializeFirebase() throws IOException {
        // 通过路径加载json 配置
        FileInputStream serviceAccount =
                new FileInputStream("src/main/resources/intelligentecommerce-758d8-firebase-adminsdk-elhud-8fca87f490.json");

        if (serviceAccount == null) {
            throw new IOException("Firebase service account file not found");
        }

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setStorageBucket("intelligentecommerce-758d8.appspot.com")  // 来自json 文件
                .build();

        return FirebaseApp.initializeApp(options);
    }
}
