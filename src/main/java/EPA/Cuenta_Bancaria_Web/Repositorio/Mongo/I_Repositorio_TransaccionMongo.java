package EPA.Cuenta_Bancaria_Web.Repositorio.Mongo;

import EPA.Cuenta_Bancaria_Web.Modelo.Mongo.M_TransaccionMongo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface I_Repositorio_TransaccionMongo extends ReactiveMongoRepository<M_TransaccionMongo, String>
{
    /*
    @Aggregation(pipeline = {})
    List<ConteoTransacciones> countTransactionsByAccount();

     */
}
