package com.github.coreyshupe;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.SplittableRandom;

public class WeightedMap<T> {
    @NotNull private final SplittableRandom random;
    @NotNull private final Map<Range, T> backingMap;
    private double top;

    public WeightedMap() {
        this(new HashMap<>());
    }

    public WeightedMap(int expectedSize) {
        this(new HashMap<>(expectedSize));
    }

    private WeightedMap(@NotNull Map<Range, T> backingMap) {
        this.random = new SplittableRandom(System.nanoTime());
        this.backingMap = backingMap;
        this.top = 0.0;
    }

    public void addItem(@NotNull WeightedItem<T> item) {
        Range range = item.buildRange(top);
        this.top = range.getEnd();
        this.backingMap.put(range, item.getItem());
    }

    public void addItem(@NotNull T item, double weight) {
        this.addItem(new WeightedItem<>(item, weight));
    }

    @NotNull public T pullRandomItem() {
        double roll = this.random.nextDouble(top);
        return backingMap.entrySet()
                .parallelStream()
                .filter(entry -> entry.getKey().isInRange(roll))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Failed to find item in range."))
                .getValue();
    }
}
