package dodam.b1nd.dgit.global.config.apolloclient;

import com.apollographql.apollo.ApolloClient;
import dodam.b1nd.dgit.global.properties.GithubProperties;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApolloClientConfig {

    @Bean
    public ApolloClient githubApolloClient(final GithubProperties githubProperties) {
        return ApolloClient.builder()
                .serverUrl("https://api.github.com/graphql")
                .okHttpClient(getOkHttpClient(githubProperties.getToken()))
                .build();
    }

    private OkHttpClient getOkHttpClient(final String token) {
        return new OkHttpClient.Builder()
                .addInterceptor(getApiKeyInterceptor(token))
                .build();
    }

    private Interceptor getApiKeyInterceptor(final String token) {
        return chain -> chain.proceed(chain.request().newBuilder()
                .addHeader("authorization", "bearer " + token)
                .build()
        );
    }
}
