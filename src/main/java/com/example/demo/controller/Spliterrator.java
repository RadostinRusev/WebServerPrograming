//package com.example.demo.controller;
//
//import com.example.demo.car.Product;
//
//import java.util.List;
//import java.util.Spliterator;
//import java.util.concurrent.atomic.AtomicInteger;
//import java.util.function.Consumer;
//
//public class Spliterrator implements Spliterator<Product> {
//    private final List<Product> list;
//    AtomicInteger current = new AtomicInteger();
//
//    public Spliterrator(List<Product> list) {
//        this.list = list;
//    }
//    // standard constructor/getters
//
//    @Override
//    public boolean tryAdvance(Consumer<? super Product> action) {
//        action.accept(list.get(current.getAndIncrement()));
//        return current.get() < list.size();
//    }
//
//    @Override
//    public Spliterator<Product> trySplit() {
//        int currentSize = list.size() - current.get();
//        if (currentSize < 10) {
//            return null;
//        }
//        for (int splitPos = currentSize / 2 + current.intValue();
//             splitPos < list.size(); splitPos++) {
//            if (list.get(splitPos).getRelatedArticleId() == 0) {
//                Spliterator<Product> spliterator
//                        = new Spliterrator(
//                        list.subList(current.get(), splitPos));
//                current.set(splitPos);
//                return spliterator;
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public long estimateSize() {
//        return list.size() - current.get();
//    }
//
//    @Override
//    public int characteristics() {
//        return CONCURRENT;
//    }
//}
