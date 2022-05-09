package sk.sandeep.newsfeedappmvvm.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sk.sandeep.newsfeedappmvvm.util_or_constants.Constants.Companion.BASE_URL

class RetrofitInstance {

    companion object {

        private val retrofit by lazy {
            /**
             * An OkHttp interceptor which logs request and response information.
            We use logging Inspector in retrofit client so we can see api request information
             * */
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
            /**
             * Use the Retrofit builder to build a retrofit object using a Gson converter
             */
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                /** converter factory for serialization and deserialization of objects. */
                .addConverterFactory(GsonConverterFactory.create())
                /** The HTTP client used for requests*/
                .client(client)
                /**
                 *Create the Retrofit instance using the configured values.
                Note: If neither client nor callFactory is called a default OkHttpClient will be created and used.
                 * */
                .build()
        }

        /**
         * A public Api object that exposes the lazy-initialized Retrofit service
         */
        val newsApi: NewsApiService by lazy {
            retrofit.create(NewsApiService::class.java)
        }
    }
}

