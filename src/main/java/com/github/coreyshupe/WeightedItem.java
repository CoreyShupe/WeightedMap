package com.github.coreyshupe;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data public class WeightedItem<T> {
    @NotNull private final T item;
    private final double weight;

    public WeightedItem(@NotNull T item, double weight) {
        this.item = item;
        if (weight == 0.0 || weight < 0.0) {
            throw new IllegalArgumentException("Given weight `" + weight + "` must be a positive non-zero number.");
        }
        this.weight = weight;
    }

    public @NotNull Range buildRange(double top) {
        return new Range(top, top + weight);
    }
}
