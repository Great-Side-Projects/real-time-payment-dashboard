package org.dev.paymentingestion.infraestructure.adapter.in.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.dev.paymentingestion.application.port.in.ITransactionServicePort;
import org.dev.paymentingestion.common.WebAdapter;
import org.dev.paymentingestion.domain.Transaction;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@WebAdapter
@RestController
@RequestMapping("/transactions")
@Tag(name = "Transactions", description = "Transaction operations")
public class TransactionController {
    private final ITransactionServicePort transactionService;

    public TransactionController(ITransactionServicePort transactionService) {
        this.transactionService = transactionService;
    }

    @Operation(summary = "Health check", description = "Check if the service is running", tags = "Transactions")
    @ApiResponse(responseCode = "200", description = "Service running")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping("/health")
    public String health() {
        return "OK";
    }

    @Operation(summary = "Process transactions",
            description = "Process a list of transactions", tags = "Transactions")
    @ApiResponse(responseCode = "200", description = "Transactions processed")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping("/process")
    public void processTransaction(
            @RequestBody
            @Parameter(description = "List of transactions")
            List<Transaction> transactions) {
        transactionService.processTransaction(transactions);
    }
}
