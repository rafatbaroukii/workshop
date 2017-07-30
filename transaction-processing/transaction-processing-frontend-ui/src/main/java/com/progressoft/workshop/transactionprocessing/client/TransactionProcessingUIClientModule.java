package com.progressoft.workshop.transactionprocessing.client;

import com.google.gwt.core.client.EntryPoint;
import com.progressoft.brix.domino.api.client.ModuleConfigurator;
import com.progressoft.brix.domino.api.client.annotations.ClientModule;
import com.progressoft.workshop.transactionprocessing.client.ui.views.Bundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.progressoft.workshop.transactionprocessing.client.ui.views.TransactionProcessingBundle;

@ClientModule(name="TransactionProcessingUI")
public class TransactionProcessingUIClientModule implements EntryPoint {

	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionProcessingUIClientModule.class);

	public void onModuleLoad() {
		LOGGER.info("Initializing TransactionProcessing frontend UI module ...");
		Bundle.INSTANCE.style().ensureInjected();
		new ModuleConfigurator().configureModule(new TransactionProcessingUIModuleConfiguration());
	}
}
