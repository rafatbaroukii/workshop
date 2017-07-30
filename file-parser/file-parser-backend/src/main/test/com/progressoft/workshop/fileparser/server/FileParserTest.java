package com.progressoft.workshop.fileparser.server;

import com.progressoft.workshop.fileparser.server.FileParser.OnNextHandler;
import com.progressoft.workshop.fileparser.shared.Transaction;
import com.progressoft.workshop.upload.shared.FileRecord;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.progressoft.workshop.fileparser.server.FileParser.InvalidFileRecordException;
import static com.progressoft.workshop.fileparser.server.FileParser.OnErrorHandler;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(VertxUnitRunner.class)
public class FileParserTest {

    @Rule
    public RunTestOnContext rule = new RunTestOnContext();

    @Before
    public void setUp() throws Exception {
        FileParserContext.withVertx(rule.vertx());
    }

    private CsvFileParserSpy parser(FileRecord fileRecord) {
        return (CsvFileParserSpy) new CsvFileParserSpyBuilder(fileRecord).build();
    }

    private CsvFileParserSpy parserWithTransactionHandler(FileRecord fileRecord, OnNextHandler onNextHandler) {
        return (CsvFileParserSpy) new CsvFileParserSpyBuilder(fileRecord).onNextTransaction(onNextHandler).build();
    }

    private CsvFileParserSpy parserWithErrorHandler(FileRecord fileRecord, OnErrorHandler onErrorHandler) {
        return (CsvFileParserSpy) new CsvFileParserSpyBuilder(fileRecord).onParsingError(onErrorHandler).build();
    }

    @Test(expected = InvalidFileRecordException.class)
    public void givenCsvFileParser_whenFileRecordIsNull_thenShouldThrowException() throws Exception {
        parser(null).parse();
    }

    @Test
    public void givenCsvFileParser_whenFileRecordHasOneRecord_thenShouldParseOneRecord(TestContext context) throws Exception {
        Async async = context.async();
        CsvFileParserSpy parserSpy = parserWithTransactionHandler(new FileRecord("test", "11111,debit,30.56\n", new Date()), transaction -> {
            assertThat(transaction.getAccountNumber()).isEqualTo("11111");
            assertThat(transaction.getOperation()).isEqualTo("debit");
            assertThat(transaction.getAmount()).isEqualTo(30.56);
        });
        parserSpy.onParsingCompleted(listener -> async.complete());
        parserSpy.parse();
    }

    @Test
    public void givenCsvFileParser_whenFileRecordHasMoreThanOneRecord_thenShouldParseAllRecords(TestContext context) throws Exception {
        Async async = context.async();
        List<Transaction> transactions = Arrays.asList(
                transaction("11111", "debit", 30.56),
                transaction("22222", "credit", 50.0));

        CsvFileParserSpy parserSpy = parserWithTransactionHandler(new FileRecord("test", "11111,debit,30.56\n22222,credit,50.0", new Date()),
                transaction -> assertThat(transactions).contains(transaction));
        parserSpy.onParsingCompleted(listener -> async.complete());
        parserSpy.parse();
    }

    private Transaction transaction(String accountNumber, String debit, double amount) {
        return new Transaction(accountNumber, debit, amount, "fileName");
    }

    @Test
    public void givenCsvFileParser_whenFileRecordHasInvalidRecord_thenShouldNotifyInvalidRecords(TestContext context) throws Exception {
        Async async = context.async();
        CsvFileParserSpy parserSpy = parserWithErrorHandler(new FileRecord("test", "invalidData\n", new Date()),
                invalidTransaction -> assertThat(invalidTransaction.getRecord()).isEqualTo("invalidData"));

        parserSpy.onParsingCompleted(listener -> async.complete());
        parserSpy.parse();
    }

    @Test
    public void givenCsvFileParser_whenFileRecordHasInvalidRecordsAndThereIsNoInvalidListener_thenShouldNotParseAnyRecord(TestContext context) throws Exception {
        Async async = context.async();
        boolean[] isThereRecords = {false};
        CsvFileParserSpy parserSpy = parserWithErrorHandler(new FileRecord("test", "invalidData\n", new Date()),
                listener -> isThereRecords[0] = true);
        parserSpy.onParsingCompleted(listener -> {
            assertThat(isThereRecords[0]).isFalse();
            async.complete();
        });
        parserSpy.parse();
    }
}
