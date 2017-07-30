package com.progressoft.workshop.uploadedfiles.client;

import com.google.gwt.core.client.EntryPoint;
import com.progressoft.brix.domino.api.client.ModuleConfigurator;
import com.progressoft.brix.domino.api.client.annotations.ClientModule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ClientModule(name="UploadedFiles")
public class UploadedFilesClientModule implements EntryPoint {

	private static final Logger LOGGER = LoggerFactory.getLogger(UploadedFilesClientModule.class);

	public void onModuleLoad() {
		LOGGER.info("Initializing UploadedFiles frontend module ...");
		new ModuleConfigurator().configureModule(new UploadedFilesModuleConfiguration());
	}
}
