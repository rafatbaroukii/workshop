package com.progressoft.workshop.upload.client;

import com.google.gwt.core.client.EntryPoint;
import com.progressoft.brix.domino.api.client.ModuleConfigurator;
import com.progressoft.brix.domino.api.client.annotations.ClientModule;
import com.progressoft.workshop.upload.client.ui.views.Bundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.progressoft.workshop.upload.client.ui.views.UploadBundle;

@ClientModule(name="UploadUI")
public class UploadUIClientModule implements EntryPoint {

	private static final Logger LOGGER = LoggerFactory.getLogger(UploadUIClientModule.class);

	public void onModuleLoad() {
		LOGGER.info("Initializing Upload frontend UI module ...");
		Bundle.INSTANCE.style().ensureInjected();
		new ModuleConfigurator().configureModule(new UploadUIModuleConfiguration());
	}
}
