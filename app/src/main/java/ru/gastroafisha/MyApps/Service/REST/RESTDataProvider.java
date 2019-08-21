package ru.gastroafisha.MyApps.Service.REST;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import ru.gastroafisha.MyApps.Service.Serializer.GMDateSerializer;
import ru.gastroafisha.MyApps.Utils.Classes.GMDate;

/**
 * Created by alex on 22.12.2017.
 */

public class RESTDataProvider {

    private IOrgService orgService;
    private ICatalogService catalogService;
    private IFormService formService;
    private IClientService clientService;

    public RESTDataProvider() {
        Retrofit retrofit = getRetrofit();

        orgService = retrofit.create(IOrgService.class);
        catalogService = retrofit.create(ICatalogService.class);
        formService = retrofit.create(IFormService.class);
        clientService = retrofit.create(IClientService.class);
    }

    public Retrofit getRetrofit() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(GMDate.class, new GMDateSerializer());

        ConnectionPool cp = new ConnectionPool(2, 1000, TimeUnit.MILLISECONDS);

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.connectionPool(cp);
        client.connectTimeout(5, TimeUnit.SECONDS);
        client.retryOnConnectionFailure(true);

        return new Retrofit.Builder()
                .baseUrl("")
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create(builder.create()))
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public IOrgService getOrgService() {
        return orgService;
    }

    public ICatalogService getCatalogService() {
        return catalogService;
    }

    public IFormService getFormService() {
        return formService;
    }

    public IClientService getClientService() {
        return clientService;
    }
}
