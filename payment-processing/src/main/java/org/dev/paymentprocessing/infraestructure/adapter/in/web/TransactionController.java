package org.dev.paymentprocessing.infraestructure.adapter.in.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.dev.paymentprocessing.application.port.ITransactionServicePort;
import org.dev.paymentprocessing.application.port.out.TransactionFilterResponse;
import org.dev.paymentprocessing.common.WebAdapter;
import org.dev.paymentprocessing.domain.TotalTransactionSummary;
import org.springframework.web.bind.annotation.*;

@WebAdapter
@RestController
@RequestMapping("/transactions")
@Tag(name = "Transactions", description = "Transaction operations")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TransactionController {
    private final ITransactionServicePort transactionService;

    public TransactionController(ITransactionServicePort transactionService) {
        this.transactionService = transactionService;
    }

    @Operation(summary = "Filter transactions",
            description = "Filter transactions by status, user id, amount, transaction id", tags = "Transactions")
    @ApiResponse(responseCode = "200", description = "Transactions filtered",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionFilterResponse.class)) })
    @GetMapping("/filter")
    public TransactionFilterResponse filterTransactions(
            @Parameter(description = "Transaction status")
            @RequestParam(required = false) String status,
            @Parameter(description = "User id")
            @RequestParam(required = false) String userid,
            @Parameter(description = "Minimum amount")
            @RequestParam(required = false) Double minamount,
            @Parameter(description = "Maximum amount")
            @RequestParam(required = false) Double maxamount,
            @Parameter(description = "Transaction id")
            @RequestParam(required = false) String transactionid,
            @Parameter(description = "Page State")
            @RequestParam(required = false) String nextpagingstate,
            @Parameter(description = "Page size")
            @RequestParam(defaultValue = "10") int size) {
        return transactionService.filterTransactions(status, userid, minamount, maxamount, transactionid, nextpagingstate, size);
    }

    @Operation(summary = "Total transaction summary",
            description = "Get total transaction summary", tags = "Transactions")
    @ApiResponse(responseCode = "200", description = "Total transaction summary",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = TotalTransactionSummary.class)) })
    @GetMapping("/summary")
    public TotalTransactionSummary totalTransactionSummary() {
        return transactionService.totalTransactionSummary();
    }

    @Operation(summary = "Total transaction summary by status",
            description = "Get total transaction summary by status", tags = "Transactions")
    @ApiResponse(responseCode = "200", description = "Total transaction summary by status",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = TotalTransactionSummary.class)) })
    @GetMapping("/summary/status/{status}")
    public TotalTransactionSummary totalTransactionSummaryByStatus(
            @Parameter(description = "Transaction status")
            @PathVariable String status) {
        return transactionService.getTransactionSummaryByStatus(status);
    }

    @Operation(summary = "Total transaction summary by user id",
            description = "Get total transaction summary by user id", tags = "Transactions")
    @ApiResponse(responseCode = "200", description = "Total transaction summary by user id",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = TotalTransactionSummary.class)) })
    @GetMapping("/summary/user/{userId}")
    public TotalTransactionSummary totalTransactionSummaryByUserId(
            @Parameter(description = "User id")
            @PathVariable String userId) {
        return transactionService.getTransactionSummaryByUserId(userId);
    }
}
