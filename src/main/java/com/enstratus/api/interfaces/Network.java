package com.enstratus.api.interfaces;

import java.util.List;
import java.util.Map;

import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Name;
import retrofit.http.POST;
import retrofit.http.SingleEntity;

import com.enstratus.api.model.Firewall;
import com.enstratus.api.model.Job;

public interface Network {
    
    @GET("/network/Firewall")
    Map<String, List<Firewall>> listFirewalls(@Name("regionId") String regionId);
    
    @POST("/network/Firewall")
    Map<String, List<Job>> addFirewall(@SingleEntity Map<String, Object> addFirewallAction);

    @DELETE("/network/Firewall/{id}")
    Map<String, List<Job>> deleteFirewall(@Name("id") String firewallId, @Name("reason") String reason);

    @POST("/network/FirewallRule")
    Map<String, List<Job>> addFirewallRule(@SingleEntity Map<String, Object> addFirewallRuleAction);
}
