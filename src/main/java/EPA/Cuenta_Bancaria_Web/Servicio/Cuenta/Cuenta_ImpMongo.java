package EPA.Cuenta_Bancaria_Web.Servicio.Cuenta;

import EPA.Cuenta_Bancaria_Web.Modelo.DTO.M_Cliente_DTO;
import EPA.Cuenta_Bancaria_Web.Modelo.DTO.M_Cuenta_DTO;
import EPA.Cuenta_Bancaria_Web.Modelo.Mongo.M_ClienteMongo;
import EPA.Cuenta_Bancaria_Web.Modelo.Mongo.M_CuentaMongo;
import EPA.Cuenta_Bancaria_Web.Repositorio.Mongo.I_RepositorioCuentaMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service()
@Qualifier("MONGO")
public class Cuenta_ImpMongo implements I_Cuenta
{
    @Autowired
    I_RepositorioCuentaMongo repositorio_Cuenta;

    @Override
    public Mono<M_Cuenta_DTO> crear_Cuenta(M_Cuenta_DTO p_Cuenta_DTO)
    {
        M_CuentaMongo cuenta = new M_CuentaMongo(p_Cuenta_DTO.getId(),
                new M_ClienteMongo(p_Cuenta_DTO.getCliente().getId(),
                        p_Cuenta_DTO.getCliente().getNombre()),
                p_Cuenta_DTO.getSaldo_Global());

        //M_Cuenta cuenta_Creada = repositorio_Cuenta.create(cuenta);
        return repositorio_Cuenta.save(cuenta).map( account ->
            new M_Cuenta_DTO(account.getId(),
                    new M_Cliente_DTO(account.getCliente().getId(),
                            account.getCliente().getNombre()),
                    account.getSaldo_Global())
        );


    }

    @Override
    public Flux<M_Cuenta_DTO> findAll()
    {
        return repositorio_Cuenta
                .findAll()
                .map(c -> new M_Cuenta_DTO(
                        c.getId(),
                        new M_Cliente_DTO(
                                c.getCliente().getId(),
                                c.getCliente().getNombre()
                        ), c.getSaldo_Global())
                );
    }


}
