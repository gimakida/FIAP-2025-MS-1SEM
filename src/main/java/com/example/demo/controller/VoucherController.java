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
@RequestMapping("/api/v1/vouchers")
@Tag(name = "Voucher", description = "API de vouchers e descontos")
public class VoucherController {
    @PostMapping
    @Operation(summary = "Criar novo voucher", description = "Cria um novo voucher")
    @ApiResponse(responseCode = "200", description = "Criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    public ResponseEntity<Voucher> createVoucher(@Valid @RequestBody Voucher voucher){
        return ResponseEntity.ok(voucher);
    }
}
