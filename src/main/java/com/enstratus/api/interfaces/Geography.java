package com.enstratus.api.interfaces;

import java.util.List;
import java.util.Map;

import retrofit.http.GET;
import retrofit.http.Name;

import com.enstratus.api.model.Cloud;
import com.enstratus.api.model.Datacenter;
import com.enstratus.api.model.Jurisdiction;
import com.enstratus.api.model.Region;

public interface Geography {
    
    @GET("/geography/Cloud")
    Map<String, List<Cloud>> listClouds();
    
    @GET("/geography/Region/{id}")
    Map<String, List<Region>> getRegion(@Name("id") String regionId, 
                                        @Name("accountId") String accountId, 
                                        @Name("jurisdiction") Jurisdiction jurisdiction, 
                                        @Name("scope") String scope);
    
    @GET("/geography/Region")
    Map<String, List<Region>> listRegions(@Name("accountId") String accountId, 
                                          @Name("jurisdiction") Jurisdiction jurisdiction,
                                          @Name("scope") String scope);
    
    @GET("/geography/DataCenter")
    Map<String, List<Datacenter>> listDatacenters(@Name("regionId") String regionId);

}