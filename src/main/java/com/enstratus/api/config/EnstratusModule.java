package com.enstratus.api.config;


import javax.inject.Singleton;

import retrofit.http.RestAdapter;
import retrofit.http.client.Client;

import com.enstratus.api.EnstratusContext;
import com.enstratus.api.features.AdminAPI;
import com.enstratus.api.features.GeographyAPI;
import com.enstratus.api.features.InfrastructureAPI;
import com.enstratus.api.features.NetworkAPI;
import com.enstratus.api.http.client.EnstratusApacheClient;

import dagger.Module;
import dagger.Provides;

@Module(entryPoints = { GeographyAPI.class, AdminAPI.class, NetworkAPI.class, InfrastructureAPI.class })
public class EnstratusModule {
    
    private final String endpoint;
    private final String accessKey;
    private final String secretKey;
    
    public EnstratusModule(EnstratusContext enstratusContext) {
        this.endpoint = enstratusContext.getEndpoint();
        this.accessKey = enstratusContext.getAccessKey();
        this.secretKey = enstratusContext.getSecretKey();
    }

    @Provides @Singleton
    Client provideClient() {
        return new EnstratusApacheClient(accessKey, secretKey);
    }

    @Provides @Singleton
    RestAdapter provideRestAdapter(Client client) {
        return new RestAdapter.Builder().setServer(endpoint).setClient(client).build();
    }

    @Provides
    GeographyAPI provideGeographyAPI(RestAdapter restAdapter) {
        return new GeographyAPI(restAdapter);
    }

    @Provides
    AdminAPI provideAdminAPI(RestAdapter restAdapter) {
        return new AdminAPI(restAdapter);
    }

    @Provides
    NetworkAPI provideNetworkAPI(RestAdapter restAdapter) {
        return new NetworkAPI(restAdapter);
    }

    @Provides
    InfrastructureAPI provideInfrastructureAPI(RestAdapter restAdapter) {
        return new InfrastructureAPI(restAdapter);
    }
    
}
