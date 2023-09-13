package com.phorest.data;

public enum ProductType {
    SERVICE("service"), PURCHASE("purchase");

    public final String value;

    ProductType(String value) {
        this.value = value;
    }
}
