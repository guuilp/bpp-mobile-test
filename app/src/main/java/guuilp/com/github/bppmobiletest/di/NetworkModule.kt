package guuilp.com.github.bppmobiletest.di

import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import guuilp.com.github.bppmobiletest.BuildConfig
import guuilp.com.github.bppmobiletest.network.LiveDataCallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofitForTwitter(gsonConverterFactory: GsonConverterFactory,
                                   okHttpClient: OkHttpClient,
                                   liveDataCallAdapterFactory: LiveDataCallAdapterFactory): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.BPP_BASE_URL)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(liveDataCallAdapterFactory)
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    fun providesLiveDataCallAdapterFactory(): LiveDataCallAdapterFactory = LiveDataCallAdapterFactory()

    @Provides
    @Singleton
    fun providesOkHttpClientForTwitter(cache: Cache): OkHttpClient {
        val client = OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)

        return client.build()
    }

    @Provides
    @Singleton
    fun providesOkhttpCache(context: Context): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 MB
        return Cache(context.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    fun providesGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun providesGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

}