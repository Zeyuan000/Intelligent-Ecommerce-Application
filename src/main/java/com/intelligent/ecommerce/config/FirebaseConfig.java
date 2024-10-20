package com.intelligent.ecommerce.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @Bean
    public Firestore getFirestore() {
        try {
            FileInputStream serviceAccount = new FileInputStream("src/main/resources/intelligentecommerce-758d8-firebase-adminsdk-elhud-8fca87f490.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("intelligentecommerce-758d8.appspot.com")
                    .build();

            FirebaseApp firebaseApp = FirebaseApp.initializeApp(options);

            return FirestoreClient.getFirestore();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to initialize Firebase", e);
        }
    }
}
