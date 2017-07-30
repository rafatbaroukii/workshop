package com.progressoft.workshop.upload.client;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.progressoft.brix.domino.api.client.ModuleConfigurator;
import com.progressoft.brix.domino.api.client.annotations.ClientModule;
import com.progressoft.workshop.fileparser.shared.FileStatistics;
import com.progressoft.workshop.upload.client.presenters.GwtJacksonJsonMapper;
import com.progressoft.workshop.upload.shared.BulkInValidTransactions;
import com.progressoft.workshop.upload.shared.BulkValidTransactions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ClientModule(name = "Upload")
public class UploadClientModule implements EntryPoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadClientModule.class);

    interface BulkValidTransactionsMapper extends ObjectMapper<BulkValidTransactions> {
    }

    interface BulkInValidTransactionsMapper extends ObjectMapper<BulkInValidTransactions> {
    }

    interface FileStatsMapper extends ObjectMapper<FileStatistics> {
    }

    public void onModuleLoad() {
        LOGGER.info("Initializing Upload frontend module ...");


        BulkValidTransactionsMapper validTransactionsMapper = GWT.create(BulkValidTransactionsMapper.class);
        BulkInValidTransactionsMapper inValidTransactionsMapper = GWT.create(BulkInValidTransactionsMapper.class);
        FileStatsMapper fileStatsMapper = GWT.create(FileStatsMapper.class);
        UploadClientContext.withBulkValidTransactionsMapper(new GwtJacksonJsonMapper<>(validTransactionsMapper));
        UploadClientContext.withInvalidTransactionsObjectMapper(new GwtJacksonJsonMapper<>(inValidTransactionsMapper));
        UploadClientContext.setFileStatsObjectMapper(new GwtJacksonJsonMapper<>(fileStatsMapper));
        new ModuleConfigurator().configureModule(new UploadModuleConfiguration());
    }
}
