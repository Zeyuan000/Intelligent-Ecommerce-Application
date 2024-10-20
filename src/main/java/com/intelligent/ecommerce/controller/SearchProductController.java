package com.intelligent.ecommerce.controller;

import com.intelligent.ecommerce.model.SearchRequest;
import com.intelligent.ecommerce.model.SearchResponse;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
public class SearchProductController {

    @Autowired
    private Firestore firestore;

    @PostMapping("/search")
    public ResponseEntity<?> searchProducts(@RequestBody SearchRequest searchRequest) {
        List<SearchResponse> responses = new ArrayList<>();

        try {
            // Search by product category and sort in descending order of sales volume
            List<QueryDocumentSnapshot> documents = firestore.collection("Products")
                    .whereEqualTo("category", searchRequest.getCategory())  // Use category to search
                    .orderBy("sales", com.google.cloud.firestore.Query.Direction.DESCENDING)
                    .limit(5)
                    .get()
                    .get()
                    .getDocuments();

            // If no matching product is found, return a prompt message
            if (documents.isEmpty()) {
                return ResponseEntity.status(404).body("Please enter the correct product category; Or use AI tools to help you generate product names.");
            }

            // Traverse the results and encapsulate the data into SearchResponse
            for (QueryDocumentSnapshot document : documents) {
                String name = document.getString("name");
                String description = document.getString("description");
                double price = document.getDouble("price");
                int sales = document.getLong("sales").intValue();
                int stock = document.getLong("stock").intValue();

                responses.add(new SearchResponse(name, description, price, sales, stock));
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred while searching for products.");
        }

        return ResponseEntity.ok(responses);
    }
}
