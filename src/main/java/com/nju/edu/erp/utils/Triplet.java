package com.nju.edu.erp.utils;

import com.sun.tools.javac.util.Pair;

public class Triplet {
    // 从指定的类型参数创建一个三元组
    public static <L, M, R> Pair<L, Pair<M, R>> of(L left, M mid, R right) {
        return Pair.of(left, Pair.of(mid, right));
    }

    // 返回三元组的左元素
    public static <L, M, R> L getLeft(Pair<L, Pair<M, R>> pair) {
        return pair.fst;
    }

    // 返回三元组的中间元素
    public static <L, M, R> M getMid(Pair<L, Pair<M, R>> pair) {
        return pair.snd.fst;
    }

    // 返回三元组的右元素
    public static <L, M, R> R getRight(Pair<L, Pair<M, R>> pair) {
        return pair.snd.snd;
    }
}