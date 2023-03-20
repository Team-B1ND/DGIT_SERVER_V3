package dodam.b1nd.dgit.global.lib.apolloclient;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.sun.istack.NotNull;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class ApolloClientUtil {

    public static <T> CompletableFuture<Response<T>> toCompletableFuture(@NotNull ApolloCall<T> apolloCall) {
        CompletableFuture<Response<T>> completableFuture = new CompletableFuture<>();

        completableFuture.whenComplete((tResponse, throwable) -> { // Future 가 complete 되었다면
            if (completableFuture.isCancelled()) { //  Future 가 정상적으로 완료되기 전에 취소 되었는지 확인
                completableFuture.cancel(true);
            }
        });

        apolloCall.enqueue(new ApolloCall.Callback<T>() {
            @Override
            public void onResponse(@NotNull Response<T> response) { // 성공
                completableFuture.complete(response);
            }

            @Override
            public void onFailure(@NotNull ApolloException e) { // 실패
                completableFuture.completeExceptionally(e);
            }
        });

        return completableFuture;
    }
}
