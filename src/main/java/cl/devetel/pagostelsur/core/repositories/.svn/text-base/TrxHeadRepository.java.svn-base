package cl.devetel.pagostelsur.core.repositories;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cl.devetel.pagostelsur.core.domain.DvtTlTrxHead;
import cl.devetel.pagostelsur.core.to.ComprobantesAnteriores;

@Repository
public interface TrxHeadRepository extends CrudRepository<DvtTlTrxHead, String> {
    
	DvtTlTrxHead findByIdtrx(String idTrx);
	
	DvtTlTrxHead findTopByOrderByIdtrxDesc();
	
	@Query("select 	distinct " +
					"new cl.devetel.pagostelsur.core.to.ComprobantesAnteriores(h.fectr, h.mnt, h.idtrx, (select count(*) from DvtTlTrxBody b where b.id.idtrx = h.idtrx))" + 
			"from DvtTlTrxHead h, DvtTlTrxBody b  " + 
			"where h.idtrx = b.id.idtrx " + 
			"and h.sta IN ('OK','ENP') " +
			"and h.idcli = :idcli " +
			"and h.idemp = :empresa"
			)
	List<ComprobantesAnteriores> findComprobantesAnteriores(@Param("idcli") String idcli, @Param("empresa") String empresa);
	
}
