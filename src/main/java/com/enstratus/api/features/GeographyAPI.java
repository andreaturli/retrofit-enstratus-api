package com.enstratus.api.features;

import java.util.List;

import javax.inject.Inject;

import retrofit.http.RestAdapter;

import com.enstratus.api.interfaces.Geography;
import com.enstratus.api.model.Cloud;
import com.enstratus.api.model.Datacenter;
import com.enstratus.api.model.Jurisdiction;
import com.enstratus.api.model.Region;

public class GeographyAPI {

    @Inject private RestAdapter restAdapter;

    public GeographyAPI(RestAdapter restAdapter) {
        this.restAdapter = restAdapter;
    }

    public List<Cloud> listClouds() {
        return restAdapter.create(Geography.class).listClouds().get("clouds");
    }
    
    public List<Region> getRegion(String regionId, String accountId, Jurisdiction jurisdiction, String scope) {
        return restAdapter.create(Geography.class).getRegion(regionId, accountId, jurisdiction, scope).get("regions");
    }    
    
    public List<Region> listRegions(String accountId, Jurisdiction jurisdiction, String scope) {
        return restAdapter.create(Geography.class).listRegions(accountId, jurisdiction, scope).get("regions");
    }
    
    public List<Datacenter> listDatacenters(String regionId) {
        return restAdapter.create(Geography.class).listDatacenters(regionId).get("dataCenters");
    }

}
