package com.luv2code.springboot.cruddemo.rest;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.springboot.cruddemo.entity.Cliente;
import com.luv2code.springboot.cruddemo.entity.Pedido;
import com.luv2code.springboot.cruddemo.service.ClienteService;

import tools.jackson.databind.json.JsonMapper;

@RestController
@RequestMapping("/api")
public class ClienteRestController {
	
	private ClienteService clienteService;
	
	private JsonMapper jsonMapper;
	
	public ClienteRestController(ClienteService clienteService, JsonMapper jsonMapper) {
		this.clienteService = clienteService;
		this.jsonMapper = jsonMapper;
	}

    @GetMapping("/clientes")
    public List<Cliente> obtenerTodos() {
        return clienteService.obtenerTodos();
    }

    @GetMapping("/clientes/{clienteId}")
    public Cliente obtenerClientePorId(@PathVariable int clienteId) {

        Cliente cliente = clienteService.obtenerPorId(clienteId);

        if (cliente == null) {
            throw new RuntimeException("No se encontro al cliente con la id - " + clienteId);
        }

        return cliente;
    }
    
    @GetMapping("/clientes/correo/{correo}")
    public Cliente obtenerClientePorCorreo(@PathVariable String correo) {

        Cliente cliente = clienteService.obtenerPorCorreo(correo);

        if (cliente == null) {
            throw new RuntimeException("No se encontro al cliente con el correo - " + correo);
        }

        return cliente;
    }
    
    @GetMapping("/clientes/{clienteId}/pedidos")
    public List<Pedido> obtenerHistorialPedidos(@PathVariable int clienteId) {

    	List<Pedido> pedidos = clienteService.obtenerHistorialPedidos(clienteId);

        return pedidos;
    }

    @PostMapping("/clientes")
    public Cliente agregarCliente(@RequestBody Cliente cliente) {

    	cliente.setId(0);

        Cliente dbCliente = clienteService.guardar(cliente);

        return dbCliente;
    }

    @PutMapping("/clientes")
    public Cliente actualizarClientePut(@RequestBody Cliente cliente) {

        Cliente dbCliente = clienteService.guardar(cliente);

        return dbCliente;
    }

    @PatchMapping("/clientes/{clienteId}")
    public Cliente actualizarClientePatch(@PathVariable int clienteId,
            @RequestBody Map<String, Object> patchPayload) {

        Cliente tempCliente = clienteService.obtenerPorId(clienteId);

        if (tempCliente == null) {
            throw new RuntimeException("No se encontro un cliente con la id  - " + clienteId);
        }

        if (patchPayload.containsKey("id")) {
            throw new RuntimeException(
                    "La id del cliente no puede ser modificada. Elimina id del request body.");
        }

        Cliente patchedCliente = jsonMapper.updateValue(tempCliente, patchPayload);

        Cliente dbCliente = clienteService.guardar(patchedCliente);

        return dbCliente;
    }
    
    @PatchMapping("/clientes/{id}/desactivar")
    public Cliente desactivarCliente(@PathVariable int id) {
        clienteService.desactivarPorId(id);
        return clienteService.obtenerPorId(id);
    }

    @DeleteMapping("/clientes/{clienteId}")
    public String borrarCliente(@PathVariable int clienteId) {

        Cliente tempCliente = clienteService.obtenerPorId(clienteId);

        if (tempCliente == null) {
            throw new RuntimeException("No se encontro un cliente con la id - " + clienteId);
        }

        clienteService.borrar(clienteId);

        return "Se borro al cliente con la id - " + clienteId;
    }
}
