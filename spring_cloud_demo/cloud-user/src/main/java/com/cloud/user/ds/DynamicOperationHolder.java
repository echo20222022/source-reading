package com.cloud.user.ds;

public class DynamicOperationHolder {

    private static final ThreadLocal<DynamicOperationType> THREAD_LOCAL = new InheritableThreadLocal<>();

    public static void put(DynamicOperationType dataSourceType) {
        THREAD_LOCAL.set(dataSourceType);
    }

    public static DynamicOperationType get() {
        return THREAD_LOCAL.get();
    }

    public static void clear() {
        THREAD_LOCAL.remove();
    }
}
