package com.rahul.project.gateway.service.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.net.ssl.*;
import java.lang.invoke.MethodHandles;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Rahul Malhotra
 * @since 1.0
 * Date 2019-05-21
 */
@Service
public class ApiServiceFactory {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    @Autowired
    private Environment environment;
    @Autowired
    @Qualifier("httpLoggingInterceptor")
    private List<HttpLoggingInterceptor> httpLoggingInterceptor;
    private Retrofit retrofit = null;

    private Retrofit retrofitGateway = null;
    private Retrofit retrofitEnableX = null;

    private Retrofit retrofitFacebook = null;

    private Retrofit retrofitGoogle = null;

    private Retrofit createService(Retrofit retrofitMethod, String URL) {
        if (retrofitMethod == null) {

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(httpLoggingInterceptor.get(0))
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build();

            Gson gson = new GsonBuilder().setLenient().create();

            retrofitMethod = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }

        return retrofitMethod;
    }

    public Retrofit getRetrofit() {
        return createService(retrofit, environment.getRequiredProperty("sms.api.url"));
    }


    public Retrofit getRetrofitGateway() {
        return createServiceGateway(retrofitGateway, environment.getRequiredProperty("gateway.api.url"));
    }

    public Retrofit getRetrofitEnableX() {
        return createServiceGateway(retrofitEnableX, environment.getRequiredProperty("enablex.api.url"));
    }

    public Retrofit getRetrofitGoogle() {
        return createService(retrofitGoogle, environment.getRequiredProperty("google.api.url"));
    }

    public Retrofit getRetrofitFacebook() {
        return createService(retrofitFacebook, environment.getRequiredProperty("facebook.api.url"));
    }

    private Retrofit createServiceGateway(Retrofit retrofitMethod, String URL) {
        if (retrofitMethod == null) {

            Gson gson = new GsonBuilder().setLenient().create();

            retrofitMethod = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(getUnsafeOkHttpClient())
                    .build();
        }

        return retrofitMethod;
    }

    private OkHttpClient getUnsafeOkHttpClient() {
        try {

            TrustManagerFactory tmf = TrustManagerFactory
                    .getInstance(TrustManagerFactory.getDefaultAlgorithm());
// Using null here initialises the TMF with the default trust store.
            tmf.init((KeyStore) null);

// Get hold of the default trust manager
            X509TrustManager x509Tm = null;
            for (TrustManager tm : tmf.getTrustManagers()) {
                if (tm instanceof X509TrustManager) {
                    x509Tm = (X509TrustManager) tm;
                    break;
                }
            }

// Wrap it in your own class.
            final X509TrustManager finalTm = x509Tm;
            X509TrustManager customTm = new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return finalTm.getAcceptedIssuers();
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                    finalTm.checkServerTrusted(chain, authType);
                }

                @Override
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                    finalTm.checkClientTrusted(chain, authType);
                }
            };

            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, customTm);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            return builder
                    .addInterceptor(httpLoggingInterceptor.get(0))
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*public Retrofit getRetrofitEnableX() {
        return createService(retrofitGateway, environment.getRequiredProperty("facebook.api.url"));
    }*/
}
