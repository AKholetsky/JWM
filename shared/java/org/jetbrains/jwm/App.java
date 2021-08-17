package org.jetbrains.jwm;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import lombok.*;
import org.jetbrains.annotations.*;
import org.jetbrains.jwm.impl.*;

public class App {
    @ApiStatus.Internal
    public static List<Window> _windows = Collections.synchronizedList(new ArrayList<Window>());

    /**
     * Call this before you do anything else
     * TODO: make idempotent
     */
    public static void init() {
        Library.load();
        _nInit();
        Log.setVerbose("true".equals(System.getenv("JWM_VERBOSE")));
        Log.setLogger(System.out::println);
    }

    @NotNull @SneakyThrows
    public static Window makeWindow() {
        Class cls;
        if (Platform.CURRENT == Platform.WINDOWS) {
            cls = App.class.forName("org.jetbrains.jwm.WindowWin32");
        } else if (Platform.CURRENT == Platform.MACOS) {
            cls = App.class.forName("org.jetbrains.jwm.WindowMac");
        } else if (Platform.CURRENT == Platform.X11) {
            cls = App.class.forName("org.jetbrains.jwm.WindowX11");
        } else
            throw new RuntimeException("Unsupported platform: " + Platform.CURRENT);
        Constructor<Window> ctor = cls.getDeclaredConstructor();
        Window window = ctor.newInstance();;
        _windows.add(window);
        return window;
    }

    /**
     * Will block until you call {@link #terminate()}
     */
    public static native int start();

    public static native void terminate();

    @ApiStatus.Internal public static native void _nInit();

    public static native Screen[] getScreens();

    public static void runOnUIThread(Runnable callback) {
        _nRunOnUIThread(callback);
    }

    public static Screen getPrimaryScreen() {
        for (Screen s: getScreens())
            if (s.isPrimary())
                return s;
        throw new IllegalStateException("Can't find primary screen");
    }

    @ApiStatus.Internal public static native void _nRunOnUIThread(Runnable callback);
}