package EPA.Cuenta_Bancaria_Web.Controlador;

import EPA.Cuenta_Bancaria_Web.Modelo.DTO.M_Cuenta_DTO;
import EPA.Cuenta_Bancaria_Web.Servicio.Cuenta.I_Cuenta;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
@RestController
@RequestMapping("Cuentas")
public class C_Cuenta
{

    @Autowired
    @Qualifier("MONGO")
    I_Cuenta serviceAccount;

    @GetMapping(value = "/listar_cuentas")
    public Flux<M_Cuenta_DTO> listar_cuentas()
    {
        return serviceAccount.findAll();
    }

    @PostMapping(value = "/Crear")
    public Mono<M_Cuenta_DTO> Crear_Cuenta(@Valid @RequestBody M_Cuenta_DTO p_cuenta)
    {
        return serviceAccount.crear_Cuenta(p_cuenta);
    }



}
