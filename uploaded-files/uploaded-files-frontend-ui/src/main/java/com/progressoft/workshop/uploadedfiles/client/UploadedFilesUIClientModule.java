package com.progressoft.workshop.uploadedfiles.client;

import com.google.gwt.core.client.EntryPoint;
import com.progressoft.brix.domino.api.client.ModuleConfigurator;
import com.progressoft.brix.domino.api.client.annotations.ClientModule;
import com.progressoft.workshop.uploadedfiles.client.ui.views.Bundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ClientModule(name="UploadedFilesUI")
public class UploadedFilesUIClientModule implements EntryPoint {

	private static final Logger LOGGER = LoggerFactory.getLogger(UploadedFilesUIClientModule.class);

	public void onModuleLoad() {
		LOGGER.info("Initializing UploadedFiles frontend UI module ...");
		Bundle.INSTANCE.style().ensureInjected();
		new ModuleConfigurator().configureModule(new UploadedFilesUIModuleConfiguration());
	}
}
