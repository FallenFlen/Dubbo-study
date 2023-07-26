package com.flz.mydubbo.api;

import java.util.concurrent.CompletableFuture;

public interface AsyncApi {
    CompletableFuture<String> fetchAsyncInfo(String msg);

    String fetchAsyncInfoWithAsyncContext(String msg);
}
