package com.jaysquared.openflights.webservice.restlet;
/**
 * 
 */


import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.jaysquared.openflights.webservice.ReleaseConfiguration;
import com.jaysquared.openflights.webservice.Version;

/**
 * @author michaelkuck
 * 
 */
public class RootRessource extends ServerResource {
	 
	@Get
    public String getRessource(){
        return ReleaseConfiguration.APPLICATION_NAME + " v." + Version.fullVersionAsString();
    }
  
}
