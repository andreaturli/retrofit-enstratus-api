package com.enstratus.api.interfaces;

import java.util.List;
import java.util.Map;

import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Name;
import retrofit.http.POST;
import retrofit.http.SingleEntity;

import com.enstratus.api.model.Job;
import com.enstratus.api.model.MachineImage;
import com.enstratus.api.model.Server;
import com.enstratus.api.model.ServerProduct;

public interface Infrastructure {
    
    @GET("/infrastructure/Server")
    Map<String, List<Server>> listServers(@Name("regionId") String regionId);
    
    @GET("/infrastructure/Server/{id}")
    Map<String, List<Server>> getServer(@Name("id") String serverId, @Name("regionId") String regionId);
    
    @POST("/infrastructure/Server")
    Map<String, List<Job>> launchServer(@SingleEntity Map<String, Object> launchServerAction);

    @DELETE("/infrastructure/Server/{id}")
    Map<String, List<Job>> deleteServer(@Name("id") String serverId, @Name("reason") String reason);

    @GET("/infrastructure/MachineImage")
    Map<String, List<MachineImage>> listMachineImages(@Name("regionId") String regionId);
    
    @GET("/infrastructure/ServerProduct")
    Map<String, List<ServerProduct>> listServerProducts(@Name("regionId") String regionId);
    
}