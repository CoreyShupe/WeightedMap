package com.github.coreyshupe;

import lombok.Data;

@Data public class Range {
    private final double start;
    private final double end;

    public boolean isInRange(double given) {
        return given >= start && given < end;
    }
}
