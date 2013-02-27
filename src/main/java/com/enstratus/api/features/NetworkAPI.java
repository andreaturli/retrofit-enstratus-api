package com.enstratus.api.features;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import retrofit.http.RestAdapter;

import com.enstratus.api.interfaces.Network;
import com.enstratus.api.model.Direction;
import com.enstratus.api.model.Firewall;
import com.enstratus.api.model.Job;
import com.enstratus.api.model.Protocol;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class NetworkAPI {

    @Inject private RestAdapter restAdapter;

    public NetworkAPI(RestAdapter restAdapter) {
        this.restAdapter = restAdapter;
    }

    public List<Firewall> listFirewalls(String regionId) {
        return restAdapter.create(Network.class).listFirewalls(regionId).get("firewalls");
    }
    
    public List<Job> addFirewall(Firewall firewall) {
        Map<String, Object> addFirewallBody = Maps.newLinkedHashMap();
        
        Map<String, Object> regionDetails = Maps.newLinkedHashMap();
        regionDetails.put("regionId", firewall.getRegion().getRegionId());
        
        Map<String, Object> addFirewallMap = Maps.newLinkedHashMap();
        addFirewallMap.put("name", firewall.getName());
        addFirewallMap.put("budget", firewall.getBudget());
        addFirewallMap.put("description", firewall.getDescription());
        addFirewallMap.put("region", regionDetails);
        
        List<Object> firewallList = Lists.newArrayList();
        firewallList.add(addFirewallMap);        
        addFirewallBody.put("addFirewall", firewallList);
        return restAdapter.create(Network.class).addFirewall(addFirewallBody).get("jobs");
    }

    public void deleteFirewall(String firewallId, String reason) {
        restAdapter.create(Network.class).deleteFirewall(firewallId, reason);
    }

    public void addFirewallRule(String firewallId, String cidr, String startPort, String endPort, Direction direction, Protocol protocol) {
        Map<String, Object> firewallRule = Maps.newLinkedHashMap();
        firewallRule.put("firewallId", firewallId);
        firewallRule.put("cidr", cidr);
        firewallRule.put("startPort", startPort);
        firewallRule.put("endPort", endPort);
        firewallRule.put("direction", direction);
        firewallRule.put("protocol", protocol);
        
        List<Object> rulesList = Lists.newArrayList();
        rulesList.add(firewallRule);
        Map<String, Object> addFirewallRuleBody = Maps.newLinkedHashMap();
        addFirewallRuleBody.put("addRule", rulesList);
        restAdapter.create(Network.class).addFirewallRule(addFirewallRuleBody).get("jobs");
    }

}
