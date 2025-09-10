package com.example.demo.service;

import com.example.demo.dto.VoucherResponse;
import com.example.demo.model.Voucher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class VoucherService {

    public VoucherResponse validateVoucher(String code) {
        // Aqui faria uma busca no repositório (DB)
        Voucher voucher = findVoucherByCode(code);

        if (voucher == null || !voucher.isAtivo() || voucher.getDataValidade().isBefore(LocalDate.now())) {
            return null;
        }

        VoucherResponse response = new VoucherResponse();
        response.setCodigo(voucher.getCodigo());
        response.setTipo(voucher.getTipo());
        response.setPercentualDesconto(voucher.getPercentualDesconto());
        response.setValorFixoDesconto(voucher.getValorFixoDesconto());
        response.setDataValidade(voucher.getDataValidade());
        response.setValorMinimoCompra(voucher.getValorMinimoCompra());
        response.setAtivo(voucher.isAtivo());
        response.setRegras("Limite de desconto percentual = R$ 100; Máximo de 40%; +5% acima de R$ 1000; Categoria PROMOCAO não aceita");

        return response;
    }

    public Map<String, Object> applyVoucher(String code, double valorTotal) {
        Map<String, Object> result = new HashMap<>();
        Voucher voucher = findVoucherByCode(code);

        if (voucher == null) {
            result.put("erro", "Voucher não encontrado");
            return result;
        }

        if (voucher.getDataValidade().isBefore(LocalDate.now())) {
            result.put("erro", "Voucher expirado");
            return result;
        }

        if (valorTotal < voucher.getValorMinimoCompra()) {
            result.put("erro", "Valor mínimo de compra não atingido");
            return result;
        }

        double desconto = 0;

        if ("PERCENTUAL".equals(voucher.getTipo())) {
            desconto = valorTotal * (voucher.getPercentualDesconto() / 100);

            // limite máximo de R$ 100 para percentuais
            if (desconto > 100) {
                desconto = 100;
            }

        } else if ("VALOR_FIXO".equals(voucher.getTipo())) {
            desconto = voucher.getValorFixoDesconto();
        }

        // Regra adicional: compras acima de 1000 = +5%
        if (valorTotal > 1000) {
            desconto += valorTotal * 0.05;
        }

        // Desconto não pode ser maior que 40% do valor total
        double maxDesconto = valorTotal * 0.40;
        if (desconto > maxDesconto) {
            desconto = maxDesconto;
        }

        double valorFinal = valorTotal - desconto;

        result.put("valorTotal", valorTotal);
        result.put("descontoAplicado", desconto);
        result.put("valorFinal", valorFinal);
        result.put("voucher", voucher.getCodigo());

        return result;
    }

    // Mock para teste (em produção viria de um repositório)
    private Voucher findVoucherByCode(String code) {
        if ("PROMO10".equalsIgnoreCase(code)) {
            Voucher v = new Voucher();
            v.setCodigo("PROMO10");
            v.setTipo("PERCENTUAL");
            v.setPercentualDesconto(15.0);
            v.setValorFixoDesconto(null);
            v.setDataValidade(LocalDate.of(2025, 12, 31));
            v.setValorMinimoCompra(100.0);
            v.setAtivo(true);
            return v;
        }
        return null;
    }

    public Voucher create(Voucher voucher) {
        // persistir no DB (mockado aqui)
        return voucher;
    }
}
