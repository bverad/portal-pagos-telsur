package cl.devetel.pagostelsur.prov.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cl.devetel.pagostelsur.prov.domain.DvtTpParamWeb;
import cl.devetel.pagostelsur.prov.domain.DvtTpParamWebPK;

@Repository
public interface ParametrosWebRepository extends CrudRepository<DvtTpParamWeb, DvtTpParamWebPK> {
    
	DvtTpParamWeb findByIdParamNom(String paramNom);
	
}
