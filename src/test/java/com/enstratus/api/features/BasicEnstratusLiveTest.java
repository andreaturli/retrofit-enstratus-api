package com.enstratus.api.features;

import static com.enstratus.api.utils.EnstratusConstants.*;
import static com.google.common.base.Preconditions.*;
import static com.google.common.collect.Iterables.tryFind;
import static java.lang.System.*;

import java.util.List;

import org.testng.Assert;

import com.enstratus.api.EnstratusContext;
import com.enstratus.api.model.BillingCode;
import com.enstratus.api.model.Datacenter;
import com.enstratus.api.model.Firewall;
import com.enstratus.api.model.Jurisdiction;
import com.enstratus.api.model.MachineImage;
import com.enstratus.api.model.Region;
import com.enstratus.api.model.Server;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;

public class BasicEnstratusLiveTest {

protected EnstratusContext enstratusContext;
    
    protected BasicEnstratusLiveTest() {
      String endpoint = checkNotNull(getProperty(ENSTRATUS_API_ENDPOINT, DEFAULT_ENDPOINT), ENSTRATUS_API_ENDPOINT);
      String accessKey = checkNotNull(getProperty(ENSTRATUS_API_ACCESS_KEY), ENSTRATUS_API_ACCESS_KEY);
      String secretKey = checkNotNull(getProperty(ENSTRATUS_API_SECRET_KEY), ENSTRATUS_API_SECRET_KEY);
      enstratusContext = new EnstratusContext(endpoint, accessKey, secretKey);
    }
    
    private List<Region> listRegionsInEU() {
        return enstratusContext.getGeographyAPI().listRegions(null, Jurisdiction.EU, null);
    }
        
    private List<Datacenter> listDatacentersInEU() {
        String regionId = getFirstRegionInEU();
        return enstratusContext.getGeographyAPI().listDatacenters(regionId);
    }

    private String getFirstRegionInEU() {
        Region region = Iterables.getFirst(listRegionsInEU(), null);
        Assert.assertNotNull(region);
        String regionId = region.getRegionId();
        return regionId;
    }

    private List<BillingCode> listBillingCodesInEU() {
        String regionId = getFirstRegionInEU();
        return enstratusContext.getAdminAPI().listBillingCodes(regionId);
    }  
    
    private List<MachineImage> listMachineImagesInEu() {
        String regionId = getFirstRegionInEU();
        return enstratusContext.getInfrastructureAPI().listMachineImages(regionId);
    }

    private List<Firewall> listFirewallsInEu() {
        String regionId = getFirstRegionInEU();
        return enstratusContext.getNetworkAPI().listFirewalls(regionId);
    }

    protected Region tryFindRegionInEUorNull() {
        return tryFind(listRegionsInEU(), Predicates.notNull()).orNull();
    }
    
    protected BillingCode tryFindBillingCodeInEuOrNull() {
        return tryFind(listBillingCodesInEU(), Predicates.notNull()).orNull();
    }
    
    protected Datacenter tryFindDatacenterInEuOrNull() {
        return tryFind(listDatacentersInEU(), Predicates.notNull()).orNull();
    }
    
    protected MachineImage tryFindMachineImageInEuOrNull() {
        return tryFind(listMachineImagesInEu(), Predicates.notNull()).orNull();
    }
    
    protected Firewall tryFindFirewallInEuOrNull() {
        return tryFind(listFirewallsInEu(), Predicates.notNull()).orNull();
    }

    protected Server createServer(String name, String description, String budget, Region region,
            Datacenter datacenter, MachineImage machineImage, List<Firewall> firewalls) {
        Server server = new Server();
        server.setName(name);
        server.setDescription(description);
        server.setBudget(budget);       
        server.setRegion(region);  
        server.setMachineImage(machineImage);
        server.setDataCenter(datacenter);
        server.setFirewalls(firewalls);
        return server;
    }
        
    protected Firewall createFirewall(String name, String description, String budget, Region region) {
        Firewall firewall = new Firewall();
        firewall.setName(name);
        firewall.setDescription(description);
        firewall.setBudget(budget);
        firewall.setRegion(region);
        return firewall;
    }    

}
