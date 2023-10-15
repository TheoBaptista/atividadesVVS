package com.example.demo;

import java.util.List;

public class Pedido {

    private List<ItemPedido> itens;
    private DescontoService descontoService;

    public Pedido(List<ItemPedido> itens, DescontoService descontoService) {
        this.itens = itens;
        this.descontoService = descontoService;
    }

    public double calcularValorTotal() {
        double valorTotal = itens.stream()
                .mapToDouble(ItemPedido::getSubtotal)
                .sum();

        double desconto = descontoService.calcularDesconto(valorTotal);
        double totalComDesconto = valorTotal - desconto;

        if (totalComDesconto < 0) {
            throw new IllegalArgumentException("O valor total não pode ser negativo.");
        }

        return totalComDesconto;
    }
}
