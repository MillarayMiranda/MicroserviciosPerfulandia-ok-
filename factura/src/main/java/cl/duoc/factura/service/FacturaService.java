package cl.duoc.factura.service;

import cl.duoc.factura.dto.UsuarioDTO;
import cl.duoc.factura.model.Factura;
import cl.duoc.factura.repository.FacturaRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Transactional
public class FacturaService {

    private final FacturaRepository facturaRepository;
    private final WebClient webClient;

    public FacturaService(FacturaRepository facturaRepository, WebClient.Builder webClientBuilder) {
        this.facturaRepository = facturaRepository;
        // Configura la URL base del microservicio de usuario
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }

    public Factura guardarFactura(Factura factura) {
        return facturaRepository.save(factura);
    }

    public List<Factura> obtenerTodasFacturas() {
        return facturaRepository.findAll();
    }

    public Factura obtenerFacturaPorId(Long id) {
        return facturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada con ID: " + id));
    }

    public List<Factura> obtenerFacturasPorUsuario(Long usuarioId) {
        return facturaRepository.findByUsuarioId(usuarioId);
    }

    // Método para consumir microservicio de usuario
    public UsuarioDTO obtenerUsuario(Long usuarioId) {
        return webClient.get()
                .uri("/usuario/{id}", usuarioId)
                .retrieve()
                .bodyToMono(UsuarioDTO.class)
                .block(); // respuesta bloqueante (sincrónica)
    }

    // Opcional: versión reactiva si la necesitas más adelante
    public Mono<Factura> obtenerFacturaCompletaReactiva(Long id) {
        return webClient.get()
                .uri("/facturas/{id}", id)
                .retrieve()
                .bodyToMono(Factura.class);
    }

    public Factura actualizarFactura(Long id, Factura facturaActualizada) {
        return facturaRepository.findById(id).map(factura -> {
            factura.setPedidoId(facturaActualizada.getPedidoId());
            factura.setUsuarioId(facturaActualizada.getUsuarioId());
            factura.setFecha(facturaActualizada.getFecha());
            factura.setTotal(facturaActualizada.getTotal());
            factura.setMetodoPago(facturaActualizada.getMetodoPago());
            return facturaRepository.save(factura);
        }).orElseThrow(() -> new RuntimeException("Factura no encontrada con ID: " + id));
    }

    // ✅ Método para eliminar factura por ID
    public void eliminarFactura(Long id) {
        Factura factura = facturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada con ID: " + id));
        facturaRepository.delete(factura);
    }
}
