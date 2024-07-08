package org.dev.paymentprocessingdashboard.infraestructure.adapter.in.web;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.dev.paymentprocessingdashboard.application.port.in.ITransactionServicePort;
import org.dev.paymentprocessingdashboard.common.WebAdapter;
import org.dev.paymentprocessingdashboard.domain.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequestMapping("/transactions")
@Tag(name = "Transactions", description = "Transaction operations")
public class TransactionController {
    private final ITransactionServicePort transactionService;

    public TransactionController(ITransactionServicePort transactionService) {
        this.transactionService = transactionService;
    }


    @Operation(summary = "Filter transactions",
            description = "Filter transactions by status, user id, amount, transaction id", tags = "Transactions")
    @ApiResponse(responseCode = "200", description = "Transactions filtered",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Transaction.class)) })
    @GetMapping("/filter")
    public Page<Transaction> filterTransactions(
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
            @Parameter(description = "Page number")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size")
            @RequestParam(defaultValue = "10") int size) {
        return transactionService.filterTransactions(status, userid, minamount, maxamount, transactionid, page, size);
    }

}
