package cl.devetel.pagostelsur.core.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cl.devetel.pagostelsur.core.domain.DvtTlTrxBody;
import cl.devetel.pagostelsur.core.domain.DvtTlTrxBodyPK;


public interface TrxBodyRepository extends CrudRepository<DvtTlTrxBody, DvtTlTrxBodyPK> {
    
	List<DvtTlTrxBody> findAllByIdIdtrx(String idTrx);
	
	
}
