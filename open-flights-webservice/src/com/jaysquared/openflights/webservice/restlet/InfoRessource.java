package com.jaysquared.openflights.webservice.restlet;

/**
 *
 */

import com.google.gson.Gson;
import com.jaysquared.openflights.webservice.ApplicationContext;
import com.jaysquared.openflights.webservice.ReleaseConfiguration;
import com.jaysquared.openflights.webservice.Version;
import com.jaysquared.openflights.webservice.dbconnection.DatabaseInformation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author michaelkuck
 */
public class InfoRessource extends ServerResource {

    public static final String   URL_ROOT                 = "/info";
    public static final String   SUB_RESOURCE_PLACEHOLDER = "sub";
    public static final String[] URLS                     = {URL_ROOT, urlForRessource(SUB_RESOURCE_PLACEHOLDER)};

    public static final String RESOURCE_VERSION = "version";

    /**
     * @return
     */
    @Get("json")
    public String getRessource() {
        final String result;
        final String ressourceSub = (String) this.getRequest().getAttributes().get(SUB_RESOURCE_PLACEHOLDER);
        if (ressourceSub == null) {
            result = this.getInformation();
        } else if (ressourceSub.equals(RESOURCE_VERSION)) {
            result = this.getVersion();
        } else {
            result = "Ressource not found";
        }
        return result;
    }

    /**
     * @return
     */
    private String getInformation() {
        final String infoText = ReleaseConfiguration.APPLICATION_NAME + " v" + Version.fullVersionAsString() + "\n" +
                                "Source: https://github.com/mikumi/OpenFlightsWebservice\n" +
                                "\n" +
                                "Data from: http://openflights.org/data.html#license\n" +
                                "\n" +
                                "The OpenFlights Airport, Airline and Route Databases are made available under the Open Database License. Any rights in individual contents of the database are licensed under the Database Contents License.\n";
        return infoText;
    }

    /**
     * @return
     */
    private String getVersion() {
        final DatabaseInformation databaseInformation = ApplicationContext.getInstance().getDatabaseInformation();
        final int databaseVersion = databaseInformation.databaseVersion();
        final String webserviceVersion = Version.fullVersionAsString();

        final Map<String, Object> version = new HashMap<String, Object>();
        version.put("webserviceVersion", webserviceVersion);
        version.put("databaseVersion", Integer.valueOf(databaseVersion));
        final Gson gson = new Gson();
        final String jsonString = gson.toJson(version);
        return jsonString;
    }

    /**
     * @param subRessourceName
     * @return
     */
    private static String urlForRessource(final String subRessourceName) {
        return URL_ROOT + "/{" + subRessourceName + "}";
    }

}
