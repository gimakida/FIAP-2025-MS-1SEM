package com.example.demo.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class VoucherRequest {
  @NotBlank(message = "Voucher code is required")
  private String codigo;

  @NotBlank(message = "Voucher type is required") // PERCENTUAL ou VALOR_FIXO
  private String tipo;

  @Min(10) @Max(30)
  private Double percentualDesconto;

  @Min(10) @Max(100)
  private Double valorFixoDesconto;

  @NotNull(message = "Data de validade é obrigatória")
  private LocalDate dataValidade;

  @Positive(message = "Valor mínimo de compra deve ser positivo")
  private Double valorMinimoCompra;
}
