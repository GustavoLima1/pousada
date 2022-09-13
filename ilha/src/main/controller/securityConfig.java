
package pousada.ilha.controller;
import java.util.Optional;

import org.generation.blogPessoal.model.UserLogin;
import org.generation.blogPessoal.model.Usuario;
import org.generation.blogPessoal.repository.UsuarioRepository;
import org.generation.blogPessoal.service.UsuarioService;
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

@RestController
@RequestMapping("/usuarios")
@CrossOrigin( origins = "*", allowedHeaders = "*")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private UsuarioRepository repository;
	
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> GetById(@PathVariable long id){
		return repository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PutMapping
	public ResponseEntity<Usuario> put(@RequestBody Usuario usuario){
		Optional<Usuario> user = Optional.ofNullable(usuarioService.atualizar(usuario)) ;

		try {
				return ResponseEntity.ok(user.get());
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping("/logar")
	public ResponseEntity<UserLogin> autentication (@RequestBody Optional<UserLogin> user){
		return usuarioService.Logar(user).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<Usuario> Post(@RequestBody Usuario usuario) {
		Optional<Usuario> user = Optional.ofNullable(usuarioService.CadastrarUsuario(usuario)) ;
		if(user.isPresent()){
				return ResponseEntity.ok(user.get());
		}else
			return ResponseEntity.badRequest().build();
		}
	
	}
	

