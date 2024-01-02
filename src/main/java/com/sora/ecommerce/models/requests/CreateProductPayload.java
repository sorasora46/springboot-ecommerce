package com.sora.ecommerce.models.requests;

import java.util.List;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateProductPayload {

    @NotBlank(message = "product's name is required.")
    @Size(min = 2, max = 50, message = "product's name must be from 2 to 50 characters.")
    private String name;

    @NotBlank(message = "product's description is required.")
    @Size(max = 255, message = "product's description should not exceed 255 characters.")
    private String description;

    @NotNull(message = "product's price is required.")
    private Float price;

    @NotNull(message = "product's amount is required.")
    @Min(1)
    private Integer amount;

    @NotNull(message = "product's image is required.")
    @Max(5)
    private List<String> images;

    public CreateProductPayload() {
    }

    public CreateProductPayload(String name, String description, Float price, Integer amount, List<String> images) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.amount = amount;
        this.images = images;
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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Boolean hasImages() {
        return images == null || images.isEmpty();
    }
}
