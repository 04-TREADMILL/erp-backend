package com.nju.edu.erp.utils;

public class Triplet<A, B, C> {

    public final A left;
    public final B mid;

    public final C right;

    public Triplet(A left, B mid, C right) {
        this.left = left;
        this.mid = mid;
        this.right = right;
    }

    public A getLeft() {
        return left;
    }

    public B getMid() {
        return mid;
    }

    public C getRight() {
        return right;
    }

    public static <A, B, C> Triplet<A, B, C> of(A a, B b, C c) {
        return new Triplet<>(a, b, c);
    }
}