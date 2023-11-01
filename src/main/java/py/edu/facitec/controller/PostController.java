package py.edu.facitec.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import py.edu.facitec.model.Post;
import py.edu.facitec.repository.PostRepository;

//Arquitectura REST
//Representational State Transfer
//Petición sin gestión de estado en el servidor
@RestController
@RequestMapping("api/") //afecta a todos los metodos
public class PostController {
	@Autowired
	private PostRepository postRepository;
	
	
	// retornara una lista de posts en formato json
	// retornar un estado de la petición
	// iniciar con la petición /api/posts (cli-ser)
	// retornar del ser al cliente un json [] (ser-cli)
	@GetMapping("posts")
	public ResponseEntity<List<Post>> getPosts(){
										//retorna la lista completa
List<Post> posts=postRepository.findAll();

		System.out.println("Ingrese al listado");
		
		
	return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);	
	}
	
	
	/*Recibir un objeto post en formato json {}
	 * 						con la url /api/post
	 * Almacenar en la base de datos
	 * Retornar el nuevo post registrado
	 * Retornar el estado de la petición  ok si funciona.
	 * 								error si no funciona
	 * */
@PostMapping("post")	
public ResponseEntity<Post>
savePost(@RequestBody Post post){
	
	try {
		Post postRetorno = postRepository.save(post);
		//emite 200
		return new ResponseEntity<Post>(postRetorno, HttpStatus.OK);
	} catch (Exception e) {
		
		e.printStackTrace();
		//si no funciona emite 500
return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);			
	}
	}

		/*
		 * Recibir el codigo de la entidad Post.
		 * por la URL /api/post
		 * Retornar un objeto post
		 * Retornar el status 200 si existe y 404 si no existe
		 * */
	

		@GetMapping("post/{codigo}")
		public ResponseEntity<Post> getById(@PathVariable Long codigo){
			System.out.println("Ingrese a consulta: "+codigo);
			Optional<Post> post =postRepository.findById(codigo);
			
			if(post.isPresent()) {
				return new ResponseEntity<Post>(post.get(), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
	
		/*
		 * Recibir un codigo.
		 * por la url api/post  DELETE
		 * 
		 * Retornar en caso de eliminar el status 200
		 * Retornar en caso de no encontrar el status 404
		 * */
	
		@DeleteMapping("post/{codigo}")
		public ResponseEntity<Post> deleteById(@PathVariable Long codigo){
			System.out.println("Ingrese a ELIMINAR: "+codigo);
			Optional<Post> post =postRepository.findById(codigo);
			
			if(post.isPresent()) {
				
				postRepository.deleteById(codigo);
				
				return new ResponseEntity<>(HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
	
	

}
