package com.abis.models.enums;

public enum BreadType {
    GREY,
    WHITE;

    @Override
    public String toString() {
        return switch (this){
            case GREY -> "grijs brood";
            case WHITE -> "witbrood";
        };
    }
}
