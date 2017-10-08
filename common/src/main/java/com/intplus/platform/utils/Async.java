package com.intplus.platform.utils;

import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import rx.Single;

public class Async {

    private Async() {

    }

    public static <T> Single<T> toSingle(ListenableFuture<T> future) {

        return Single.<T>create(subscriber -> future.addCallback(new ListenableFutureCallback<T>() {
            @Override
            public void onFailure(Throwable throwable) {
                subscriber.onError(throwable);
            }

            @Override
            public void onSuccess(T result) {
                subscriber.onSuccess(result);
            }
        }));
    }
}
