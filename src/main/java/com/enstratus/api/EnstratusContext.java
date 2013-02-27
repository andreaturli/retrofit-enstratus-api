package com.enstratus.api;

import static com.google.common.base.Preconditions.*;
import com.enstratus.api.config.EnstratusModule;
import com.enstratus.api.features.AdminAPI;
import com.enstratus.api.features.GeographyAPI;
import com.enstratus.api.features.InfrastructureAPI;
import com.enstratus.api.features.NetworkAPI;

import dagger.ObjectGraph;

public class EnstratusContext {
   
    private final ObjectGraph objectGraph;
    private final String endpoint;
    private final String accessKey;
    private final String secretKey;
    
    public EnstratusContext(String endpoint, String accessKey, String secretKey) {
        this.endpoint = checkNotNull(endpoint, "endpoint");
        this.accessKey = checkNotNull(accessKey, "accessKey");
        this.secretKey = checkNotNull(secretKey, "secretKey");
        this.objectGraph = ObjectGraph.create(new EnstratusModule(this));
    }
    
    public GeographyAPI getGeographyAPI() {
        return objectGraph.get(GeographyAPI.class);
    }

    public AdminAPI getAdminAPI() {
        return objectGraph.get(AdminAPI.class);
    }

    public NetworkAPI getNetworkAPI() {
        return objectGraph.get(NetworkAPI.class);
    }

    public InfrastructureAPI getInfrastructureAPI() {
        return objectGraph.get(InfrastructureAPI.class);
    }
    
    public String getAccessKey() {
        return accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getEndpoint() {
        return endpoint;
    }

}
