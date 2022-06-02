package br.com.jotabelk.controllers;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.jotabelk.entities.Membro;
import br.com.jotabelk.repositories.IMembroRepository;
import br.com.jotabelk.request.MembroPostRequest;
import br.com.jotabelk.request.MembroPutRequest;
import io.swagger.annotations.ApiOperation;

@Transactional
@Controller
public class MembroController {

	@Autowired // a interface inicializa automatico
	private IMembroRepository membroRepository;

	private static final String ENDPOINT = "/api/members";

	@ApiOperation("Metodo para realizar o cadastro de um membro.")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
	public ResponseEntity<String> post(@RequestBody MembroPostRequest request) {

		try {
			if (membroRepository.findByCpf(request.getCpf()) != null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("O cpf " + request.getCpf() + "já esta cadastrado no sistema, tente outro.");
			}
			if (membroRepository.findByEmail(request.getEmail()) != null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("O email " + request.getCpf() + "já esta cadastrado no sistema, tente outro.");
			}

			Membro membro = new Membro();

			membro.setNome(request.getNome());
			membro.setCpf(request.getCpf());
			membro.setEmail(request.getEmail());
			membroRepository.save(membro);
			return ResponseEntity.status(HttpStatus.OK)// HTTP 200
					.body("membro cadastrado com sucesso.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)// HTTP 500
					.body(e.getMessage());
		}

	}

	@ApiOperation("Metodo para realizar a atualizacao de um membro.")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.PUT)
	public ResponseEntity<String> put(@RequestBody MembroPutRequest request) {
		

		try {
			Optional<Membro> consulta = membroRepository.findById(request.getIdMembro());

			if (consulta.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)// HTTP 400
						.body("nenhum membro encontrado, verifique o Id informado.");
			}
			Membro membro = consulta.get();
			membro.setNome(request.getNome());
			membroRepository.save(membro);

			return ResponseEntity.status(HttpStatus.OK)// HTTP 200
					.body("membro atualizado com sucesso.");

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)// HTTP 500
					.body(e.getMessage());
		}

	}

	@ApiOperation("Metodo para excluir um membro.")
	@RequestMapping(value = ENDPOINT + "/{idMembres}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable("idMembro") Integer idMembro) {

		try {
			Optional<Membro> consulta = membroRepository.findById(idMembro);

			if (consulta.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)// HTTP 400
						.body("nenhum membro encontrado, verifique o Id informado.");
			}
			Membro membro = consulta.get();
			// excluindo o membro
			membroRepository.delete(membro);

			return ResponseEntity.status(HttpStatus.OK)// HTTP 200
					.body("membro foi excluido com sucesso.");

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)// HTTP 500
					.body(e.getMessage());
		}

	}

	@ApiOperation("Metodo para consultar todos os membros.")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.GET)
	public ResponseEntity<List<Membro>> getAll() {

		try {

			List<Membro> membros = (List<Membro>) membroRepository.findAll();
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(membros);

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)// HTTP 500
					.body(null);

		}

	}

	@ApiOperation("Metodo para consultar um membro pelo no ID.")
	@RequestMapping(value = ENDPOINT + "/{idMembres}", method = RequestMethod.GET)
	public ResponseEntity<Membro> getById(@PathVariable("idMembers") Integer idMembro) {

		try {

			Optional<Membro> consulta = membroRepository.findById(idMembro);
			if (consulta.isPresent()) {
				Membro membro = consulta.get();
				return ResponseEntity.status(HttpStatus.OK).body(membro);
			}

			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)// HTTP 500
					.body(null);

		}
	}
}
