package fi.nls.oskari.control.statistics.user;

import fi.nls.oskari.control.*;
import fi.nls.oskari.control.statistics.StatisticsHelper;
import fi.nls.oskari.control.statistics.data.*;
import fi.nls.oskari.control.statistics.plugins.StatisticalDatasourcePlugin;
import fi.nls.oskari.control.statistics.plugins.StatisticalDatasourcePluginManager;
import fi.nls.oskari.log.Logger;
import fi.nls.oskari.service.OskariComponentManager;
import org.json.JSONException;
import org.oskari.statistics.user.StatisticalIndicatorService;
import fi.nls.oskari.annotation.OskariActionRoute;
import fi.nls.oskari.log.LogFactory;
import fi.nls.oskari.util.JSONHelper;
import fi.nls.oskari.util.ResponseHelper;

@OskariActionRoute("SaveIndicator")
public class SaveIndicatorHandler extends ActionHandler {

    private StatisticalIndicatorService indicatorService;

    @Override
    public void init() {
        super.init();
        if (indicatorService == null) {
            indicatorService = OskariComponentManager.getComponentOfType(StatisticalIndicatorService.class);
        }
    }

    public static String PARAM_NAME = "name";
    public static String PARAM_DESCRIPTION = "desc";
    public static String PARAM_SOURCE = "source";

    private static final Logger log = LogFactory.getLogger(SaveIndicatorHandler.class);

    public void handleAction(ActionParameters params) throws ActionException {
        params.requireLoggedInUser();

        int datasourceId = params.getRequiredParamInt(StatisticsHelper.PARAM_DATASOURCE_ID);
        StatisticalDatasourcePlugin datasource = StatisticalDatasourcePluginManager.getInstance().getPlugin(datasourceId);
        if(datasource == null) {
            throw new ActionParamsException("Invalid datasource:" + datasourceId);
        }
        if(!datasource.canModify(params.getUser())) {
            throw new ActionDeniedException("User doesn't have permission to modify datasource:" + datasourceId);
        }
        String id = params.getHttpParam(ActionConstants.PARAM_ID);
        StatisticalIndicator existingIndicator = null;
        if (id != null) {
            existingIndicator = datasource.getIndicator(params.getUser(), id);
            if(existingIndicator == null) {
                // indicator removed or is not owned by this user
                throw new ActionParamsException("Requested invalid indicator:" + id);
            }
        }
        StatisticalIndicator indicator = parseIndicator(params, existingIndicator);
        try {
            existingIndicator = datasource.saveIndicator(indicator, params.getUser());
        } catch (Exception ex) {
            throw new ActionException("Couldn't save indicator", ex);
        }

        try {
            ResponseHelper.writeResponse(params, StatisticsHelper.toJSON(existingIndicator));
        } catch (JSONException shouldNeverHappen) {
            ResponseHelper.writeResponse(params, JSONHelper.createJSONObject("id", id));
        }
    }

    private StatisticalIndicator parseIndicator(ActionParameters params, StatisticalIndicator existingIndicator) {
        StatisticalIndicator ind = existingIndicator;
        if(ind == null) {
            ind = new StatisticalIndicator();
        }
        // always set to true, but doesn't really matter?
        // TODO: should this be the toggle if guest users can see the indicator on embedded maps?
        ind.setPublic(true);

        String language = params.getLocale().getLanguage();
        ind.addName(language, params.getHttpParam(PARAM_NAME, ind.getName(language)));
        ind.addDescription(language, params.getHttpParam(PARAM_DESCRIPTION, ind.getDescription(language)));
        ind.addSource(language, params.getHttpParam(PARAM_SOURCE, ind.getSource(language)));
        return ind;
    }

}
