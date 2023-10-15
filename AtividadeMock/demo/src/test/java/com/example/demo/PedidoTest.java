package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class PedidoTest {

    private DescontoService descontoService;

    @BeforeEach
    public void setUp() {
        descontoService = Mockito.mock(DescontoService.class);
    }

    @Test
    void testCalcularValorTotalComDesconto() {
        List<ItemPedido> itens = new ArrayList<>();
        itens.add(new ItemPedido("Item 1", 100.0,1));
        itens.add(new ItemPedido("Item 2", 200.0,1));

        // Configura o mock para retornar um desconto de 10%
        when(descontoService.calcularDesconto(300.0)).thenReturn(30.0);

        var pedido = new Pedido(itens, descontoService);

        double valorTotalComDesconto = pedido.calcularValorTotal();

        assertEquals(270.0, valorTotalComDesconto);
    }

    @Test
    void testCalcularValorTotalSemDesconto() {
        List<ItemPedido> itens = new ArrayList<>();
        itens.add(new ItemPedido("Item 1", 100.0,1));
        itens.add(new ItemPedido("Item 2", 200.0,1));

        // Configura o mock para retornar desconto zero
        when(descontoService.calcularDesconto(300.0)).thenReturn(0.0);

         var pedido = new Pedido(itens, descontoService);

        double valorTotalSemDesconto = pedido.calcularValorTotal();

        assertEquals(300.0, valorTotalSemDesconto);
    }

//    @Test
//    void testCalcularValorTotalComDescontoMaiorQueTotal() {
//        List<ItemPedido> itens = new ArrayList<>();
//        itens.add(new ItemPedido("Item 1", 100.0,1));
//        itens.add(new ItemPedido("Item 2", 200.0,1));
//
//        // Configura o mock para retornar um desconto maior que o valor total
//        when(descontoService.calcularDesconto(300.0)).thenReturn(301.0);
//
//        var pedido = new Pedido(itens, descontoService);
//
//        double valorTotal = pedido.calcularValorTotal();
//
//        assertEquals(0.0, valorTotal);
//    }

    @Test
    void testCalcularValorTotalComExcecao() {
        List<ItemPedido> itens = new ArrayList<>();
        itens.add(new ItemPedido("Item 1", 100.0,1));
        itens.add(new ItemPedido("Item 2", 200.0,1));

        // Configura o mock para retornar um desconto maior que o valor total
        when(descontoService.calcularDesconto(300.0)).thenReturn(400.0);

         var pedido = new Pedido(itens, descontoService);

        assertThrows(IllegalArgumentException.class, () -> pedido.calcularValorTotal());
    }

    @Test
    void testCalcularValorTotalComListaVazia() {
        List<ItemPedido> itens = new ArrayList<>();

        var pedido = new Pedido(itens, descontoService);

        double valorTotal = pedido.calcularValorTotal();

        assertEquals(0.0, valorTotal);
    }

    @Test
    void testCalcularValorTotalComDescontosDiferentes() {
        List<ItemPedido> itens = new ArrayList<>();
        List<ItemPedido> itens2 = new ArrayList<>();
        itens.add(new ItemPedido("Item 1", 100.0,1));
        itens.add(new ItemPedido("Item 2", 200.0,1));
        itens2.add(new ItemPedido("Item 3", 200.0,1));
        itens2.add(new ItemPedido("Item 4", 200.0,1));

        // Configura o mock para diferentes descontos para cada item
        when(descontoService.calcularDesconto(300.0)).thenReturn(30.0);
        when(descontoService.calcularDesconto(400.0)).thenReturn(40.0);

        var pedido = new Pedido(itens, descontoService);
        var pedido2 = new Pedido(itens2, descontoService);

        double valorTotal = pedido.calcularValorTotal();
        double valorTotal2 = pedido2.calcularValorTotal();

        assertEquals(270.0, valorTotal);
        assertEquals(360.0, valorTotal2);
    }

    @Test
    void testChamarCalcularDescontoUmaVez() {
        List<ItemPedido> itens = new ArrayList<>();
        itens.add(new ItemPedido("Item 1", 100.0,1));
        itens.add(new ItemPedido("Item 2", 200.0,1));

        // Configura o mock para retornar um desconto de 10%
        when(descontoService.calcularDesconto(300.0)).thenReturn(30.0);

        var pedido = new Pedido(itens, descontoService);

        pedido.calcularValorTotal();

        // Verifica se o método calcularDesconto foi chamado uma vez
        Mockito.verify(descontoService, Mockito.times(1)).calcularDesconto(300.0);
    }
}