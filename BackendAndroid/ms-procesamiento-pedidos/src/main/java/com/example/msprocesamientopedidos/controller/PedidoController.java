package com.example.msprocesamientopedidos.controller;

import com.example.msprocesamientopedidos.dto.Cliente;
import com.example.msprocesamientopedidos.entity.Pedido;
import com.example.msprocesamientopedidos.service.PedidoService;
import com.example.msprocesamientopedidos.service.PdfGeneratorService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    @GetMapping()
    public ResponseEntity<List<Pedido>> list() {
        return ResponseEntity.ok().body(pedidoService.listar());
    }

    @PostMapping()
    public ResponseEntity<Pedido> save(@RequestBody Pedido pedido) {
        return ResponseEntity.ok(pedidoService.guardar(pedido));
    }

    @PutMapping()
    public ResponseEntity<Pedido> update(@RequestBody Pedido pedido) {
        return ResponseEntity.ok(pedidoService.actualizar(pedido));
    }

    @CircuitBreaker(name = "pedidoListarPorIdCB", fallbackMethod = "fallBackPedidoListarPorIdCB")
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> listById(@PathVariable(required = true) Integer id) {
        return ResponseEntity.ok().body(pedidoService.listarPorId(id).get());
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable(required = true) Integer id) {
        pedidoService.eliminarPorId(id);
        return "Eliminacion Correcta";
    }

    @PostMapping("/procesar/{userId}")
    public ResponseEntity<Pedido> procesarPedido(@PathVariable Integer userId) {
        Pedido pedido = pedidoService.procesarPedido(userId);
        return ResponseEntity.ok(pedido);
    }

    @GetMapping("/pdf/{id}")
    public ResponseEntity<byte[]> generatePdf(@PathVariable Integer id) {
        Pedido pedido = pedidoService.listarPorId(id).orElse(null);
        if (pedido == null) {
            return ResponseEntity.notFound().build();
        }

        String pdfPath = "pedido_" + id + ".pdf";
        pdfGeneratorService.generatePdf(pedido, pdfPath);

        try {
            Path path = Paths.get(pdfPath);
            byte[] pdfBytes = Files.readAllBytes(path);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + pdfPath);

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                    .body(pdfBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    private ResponseEntity<Pedido> fallBackPedidoListarPorIdCB(@PathVariable(required = true) Integer id, RuntimeException e) {
        Pedido pedido = new Pedido();
        pedido.setId(90000);
        Cliente cliente = new Cliente();
        cliente.setNombre("Recurso no disponible del nombre del cliente");
        cliente.setDireccion("no tiene direccion");
        pedido.setCliente(cliente);
        return ResponseEntity.ok().body(pedido);
    }
}
