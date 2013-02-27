package com.enstratus.api.features;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import retrofit.http.RestAdapter;

import com.enstratus.api.interfaces.Infrastructure;
import com.enstratus.api.model.Job;
import com.enstratus.api.model.MachineImage;
import com.enstratus.api.model.Server;
import com.enstratus.api.model.ServerProduct;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class InfrastructureAPI {

    @Inject private RestAdapter restAdapter;

    public InfrastructureAPI(RestAdapter restAdapter) {
        this.restAdapter = restAdapter;
    }

    public List<Server> listServers(String regionId) {
        return restAdapter.create(Infrastructure.class).listServers(regionId).get("servers");
    }
    
    public List<MachineImage> listMachineImages(String regionId) {
        return restAdapter.create(Infrastructure.class).listMachineImages(regionId).get("images");
    }
    
    public List<ServerProduct> listServerProducts(String regionId) {
        return restAdapter.create(Infrastructure.class).listServerProducts(regionId).get("serverProducts");
    }    
    
    public List<Job> launchServer(Server server) {
        Map<String, Object> launchServerBody = Maps.newLinkedHashMap();
        List<Object> servers = Lists.newArrayList();
        servers.add(server); 
        launchServerBody.put("launch", servers);
        return restAdapter.create(Infrastructure.class).launchServer(launchServerBody).get("jobs");
    }
    
    public void deleteServer(String serverId, String reason) {
        restAdapter.create(Infrastructure.class).deleteServer(serverId, reason);
    }

}
