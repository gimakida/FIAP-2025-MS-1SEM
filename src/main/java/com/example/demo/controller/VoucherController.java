package com.example.demo.controller;

import com.example.demo.dto.VoucherResponse;
import com.example.demo.model.Voucher;
import com.example.demo.service.VoucherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/vouchers")
@Tag(name = "Voucher", description = "Voucher management and validation APIs")
public class VoucherController {

    private static final String SUCCESS_CODE = "200";
    private static final String NOT_FOUND_CODE = "404";
    private static final String BAD_REQUEST_CODE = "400";
    private static final String NOT_FOUND_MESSAGE = "Voucher not found";

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/{code}")
    @Operation(
        summary = "Validate and get voucher details",
        description = "Checks if a voucher exists, is valid, and returns its rules and details"
    )
    @ApiResponse(responseCode = SUCCESS_CODE, description = "Successfully retrieved voucher")
    @ApiResponse(responseCode = NOT_FOUND_CODE, description = NOT_FOUND_MESSAGE)
    @ApiResponse(responseCode = BAD_REQUEST_CODE, description = "Voucher expired or not applicable")
    public ResponseEntity<VoucherResponse> getVoucher(@PathVariable String code) {
        VoucherResponse response = voucherService.validateVoucher(code);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @PostMapping("/{code}/apply")
    @Operation(
        summary = "Apply voucher to purchase",
        description = "Validates and applies voucher rules to a given cart total"
    )
    @ApiResponse(responseCode = SUCCESS_CODE, description = "Successfully applied voucher")
    @ApiResponse(responseCode = BAD_REQUEST_CODE, description = "Voucher invalid or not applicable")
    public ResponseEntity<Map<String, Object>> applyVoucher(
            @PathVariable String code,
            @RequestParam double valorTotal
    ) {
        Map<String, Object> result = voucherService.applyVoucher(code, valorTotal);
        if (result.containsKey("erro")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping
    @Operation(summary = "Create a new voucher", description = "Creates a new voucher in the system")
    @ApiResponse(responseCode = SUCCESS_CODE, description = "Successfully created voucher")
    public Voucher createVoucher(@Valid @RequestBody Voucher voucher) {
        return voucherService.create(voucher);
    }
}
