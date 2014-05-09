package com.landa.customer;

import com.squareup.otto.Bus;

public final class Broadcast_Singleton {
    private static final Broadcast_Singleton INSTANCE = new Broadcast_Singleton();
    private static Bus mEventBus;
    
    private Broadcast_Singleton() {
    	Broadcast_Singleton.mEventBus = new Bus();
    }
    
    public static Bus getEventBus() {
        return mEventBus;
    }
    public static Broadcast_Singleton getInstance() {
        return INSTANCE;
    }
    
}
