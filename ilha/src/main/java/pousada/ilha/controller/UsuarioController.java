
package pousada.ilha.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pousada.ilha.model.UserLogin;
import pousada.ilha.model.UsuarioDTO;
import pousada.ilha.repository.UsuarioRepository;
import pousada.ilha.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private UsuarioRepository repository;

	@GetMapping("/{id}")
	public ResponseEntity<UsuarioDTO> getById(@PathVariable long id) {
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}

	@PutMapping
	public ResponseEntity<UsuarioDTO> put(@RequestBody UsuarioDTO usuario) {
		Optional<UsuarioDTO> user = Optional.ofNullable(usuarioService.atualizar(usuario));

		try {
			return ResponseEntity.ok(user.get());
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@PostMapping("/logar")
	public ResponseEntity<UserLogin> autentication(@RequestBody Optional<UserLogin> user) {
		return usuarioService.logar(user).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

	@PostMapping("/cadastrar")
	public ResponseEntity<UsuarioDTO> post(@RequestBody UsuarioDTO usuario) {
		Optional<UsuarioDTO> user = Optional.ofNullable(usuarioService.cadastrarUsuario(usuario));
		if (user.isPresent()) {
			return ResponseEntity.ok(user.get());
		} else
			return ResponseEntity.badRequest().build();
	}

}
