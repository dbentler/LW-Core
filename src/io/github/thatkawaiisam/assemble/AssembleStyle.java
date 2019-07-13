package io.github.thatkawaiisam.assemble;

import lombok.Getter;

@Getter
public enum AssembleStyle {

    LONEWOLVES(true, 15),
    VIPER(true, -1),
    MODERN(false, 1);

    private boolean decending;
    private int startNumber;

    AssembleStyle(boolean decending, int startNumber) {
        this.decending = decending;
        this.startNumber = startNumber;
    }
}
