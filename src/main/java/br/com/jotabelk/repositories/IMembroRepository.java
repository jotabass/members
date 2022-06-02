package br.com.jotabelk.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.jotabelk.entities.Membro;

public interface IMembroRepository extends CrudRepository<Membro, Integer>{
	
	@Query("select e from Membro  e where e.cpf = :param1")
	Membro findByCpf(@Param("param1") String cpf) throws Exception;
	
	@Query("select e from Membro  e where e.email = :param1")
	Membro findByEmail(@Param("param1") String email) throws Exception;

}
