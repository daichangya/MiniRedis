package com.daicy.collections;

class Box<T> {
    private T boxed;

    public void box(T boxed) {
        this.boxed = boxed;
    }

    public T unbox() {
        return boxed;
    }
}
