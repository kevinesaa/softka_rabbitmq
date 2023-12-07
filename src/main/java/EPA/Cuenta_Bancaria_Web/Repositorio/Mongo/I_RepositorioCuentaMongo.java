package EPA.Cuenta_Bancaria_Web.Repositorio.Mongo;

import EPA.Cuenta_Bancaria_Web.Modelo.Mongo.M_CuentaMongo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface I_RepositorioCuentaMongo extends ReactiveMongoRepository<M_CuentaMongo, String>
{

}
