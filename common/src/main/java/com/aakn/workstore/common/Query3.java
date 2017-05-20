package com.aakn.workstore.common;

public interface Query3<T, U, V, R> {

  R apply(T t, U u, V v);

}
