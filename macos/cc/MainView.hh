#pragma once
#import <Cocoa/Cocoa.h>
#include "WindowMac.hh"
#include "Key.hh"

namespace jwm {
    extern Key kKeyTable[];
    void initKeyTable();
}

@interface MainView : NSView <NSTextInputClient>

- (MainView*)initWithWindow:(jwm::WindowMac*)initWindow;

@end
