package EPA.Cuenta_Bancaria_Web.Servicio.Transaccion;

import EPA.Cuenta_Bancaria_Web.Modelo.DTO.M_Cliente_DTO;
import EPA.Cuenta_Bancaria_Web.Modelo.DTO.M_Cuenta_DTO;
import EPA.Cuenta_Bancaria_Web.Modelo.DTO.M_Transaccion_DTO;
import EPA.Cuenta_Bancaria_Web.Modelo.Enum_Tipos_Deposito;
import EPA.Cuenta_Bancaria_Web.Modelo.Mongo.M_CuentaMongo;
import EPA.Cuenta_Bancaria_Web.Modelo.Mongo.M_TransaccionMongo;
import EPA.Cuenta_Bancaria_Web.Repositorio.Mongo.I_RepositorioCuentaMongo;
import EPA.Cuenta_Bancaria_Web.Repositorio.Mongo.I_Repositorio_TransaccionMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
@Qualifier("MONGO")
public class Transaccion_ImpMongo implements I_Transaccion
{
    @Autowired
    I_Repositorio_TransaccionMongo transaccion_repositorio;

    @Autowired
    I_RepositorioCuentaMongo cuenta_repositorio;

    @Override
    public Mono<M_Transaccion_DTO> Procesar_Deposito(String id_Cuenta, Enum_Tipos_Deposito tipo, BigDecimal monto)
    {
        final BigDecimal costo = tipo.getCosto();

         return cuenta_repositorio.findById(id_Cuenta)
                .map(acc -> {

                    BigDecimal bdSaldoNuevo = acc.getSaldo_Global().add(monto.subtract(costo));
                    M_TransaccionMongo mTransaccionMongo = new M_TransaccionMongo(
                            acc,
                            monto,
                            acc.getSaldo_Global(),
                            bdSaldoNuevo,
                            costo,
                            tipo.toString()
                    );
                    acc.setSaldo_Global(bdSaldoNuevo);
                    return transaccion_repositorio.save(mTransaccionMongo)
                            .flatMap(t -> {
                                return cuenta_repositorio.save(acc).map(a -> t);
                            });

                })
                .flatMap(t -> t)
                .map(dbTransaction -> new M_Transaccion_DTO(dbTransaction.getId(),
                        new M_Cuenta_DTO(
                                dbTransaction.getCuenta().getId(),
                                new M_Cliente_DTO(
                                        dbTransaction.getCuenta().getCliente().getId(),
                                        dbTransaction.getCuenta().getCliente().getNombre()
                                ),
                                dbTransaction.getCuenta().getSaldo_Global()
                        ),
                        dbTransaction.getMonto_transaccion(),
                        dbTransaction.getSaldo_inicial(),
                        dbTransaction.getSaldo_final(),
                        dbTransaction.getCosto_tansaccion(),
                        dbTransaction.getTipo()
                ));
    }

    @Override
    public Flux<M_Transaccion_DTO> findAll()
    {
        return transaccion_repositorio.findAll()
                .map(transaccion -> {
                    return new M_Transaccion_DTO(transaccion.getId(),
                            new M_Cuenta_DTO(transaccion.getCuenta().getId(),
                                    new M_Cliente_DTO(transaccion.getCuenta().getCliente().getId(),
                                            transaccion.getCuenta().getCliente().getNombre()
                                    ),
                                    transaccion.getCuenta().getSaldo_Global()
                            ),
                            transaccion.getMonto_transaccion(),
                            transaccion.getSaldo_inicial(),
                            transaccion.getSaldo_final(),
                            transaccion.getCosto_tansaccion(),
                            transaccion.getTipo()
                    );
                });
    }
}
