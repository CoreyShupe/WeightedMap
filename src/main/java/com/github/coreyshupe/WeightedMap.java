package com.github.coreyshupe;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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

    public @NotNull Optional<T> pullRandomItem() {
        if (backingMap.isEmpty()) return Optional.empty();
        double roll = this.random.nextDouble(top);
        return backingMap.entrySet()
                .parallelStream()
                .filter(entry -> entry.getKey().isInRange(roll))
                .findFirst()
                .map(Map.Entry::getValue);
    }
}
