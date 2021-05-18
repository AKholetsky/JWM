package org.jetbrains.jwm;

import java.util.function.*;
import lombok.*;
import org.jetbrains.annotations.*;
import org.jetbrains.jwm.*;
import org.jetbrains.jwm.impl.*;

public class LayerGL extends Layer {
    public LayerGL() {
        super(_nMake());
    }

    @ApiStatus.Internal public static native long _nMake();
}