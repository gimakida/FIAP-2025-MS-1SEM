package com.example.demo.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class VoucherResponse {
  private String codigo;
  private String tipo;
  private Double percentualDesconto;
  private Double valorFixoDesconto;
  private LocalDate dataValidade;
  private Double valorMinimoCompra;
  private boolean ativo;
  private String regras; // descrição resumida das regras aplicadas
}
