package com.intelligent.ecommerce.model;

public class SearchResponse {
    private String name;
    private String description;
    private double price;
    private int sales;
    private int stock;

    // Constructor, Getters, and Setters
    public SearchResponse(String name, String description, double price, int sales, int stock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.sales = sales;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
