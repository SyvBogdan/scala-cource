package net.study.functional.lesson_14_futures;

import java.util.concurrent.*;

public class JavaFuture {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //// completable already completed! apply ()
        final CompletableFuture<String> completableFuture = CompletableFuture.completedFuture("Hello");
        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() -> "world!");

        ///// analogue of flatMap
        final CompletableFuture<String> flatMapResult =
                completableFuture.thenComposeAsync(r -> completableFuture2.thenApply( l -> l + r));

        //// example of writable future or Promise in scala api
        final CompletableFuture<String> notCompletableFuture = new CompletableFuture<>();


        final CompletableFuture<Void> allOf = CompletableFuture.allOf(CompletableFuture.runAsync(() -> {
           throw  new RuntimeException("Exception");
        }),completableFuture);

        System.out.println(allOf.get());

    }


    interface Listener<T> {

        void onSuccess(T value);

        void onFailure(Throwable ex);

    }


    static <IN, T> void someAsyncMethod(IN in, Listener<T> callBack) {

    }

    CompletionStage<String> getAsyncResult(String in) {

        final CompletableFuture<String> completable = new CompletableFuture<>();

        final Listener<String> onComplete = new Listener<>() {
            @Override
            public void onSuccess(String value) {
                completable.complete(value);
            }

            @Override
            public void onFailure(Throwable ex) {
                completable.completeExceptionally(ex);
            }
        };

        // non blocking !!!!!!!!
        someAsyncMethod(in, onComplete);

        return completable;
    }

}
