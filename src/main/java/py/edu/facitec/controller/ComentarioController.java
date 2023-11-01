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

import py.edu.facitec.model.Comentario;
import py.edu.facitec.repository.ComentarioRepository;

//Arquitectura REST
//Representational State Transfer
//Petición sin gestión de estado en el servidor
@RestController
@RequestMapping("api/") //afecta a todos los metodos
public class ComentarioController {
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	
	// retornara una lista de comentarios en formato json
	// retornar un estado de la petición
	// iniciar con la petición /api/comentarios (cli-ser)
	// retornar del ser al cliente un json [] (ser-cli)
	@GetMapping("comentarios")
	public ResponseEntity<List<Comentario>> getComentarios(){
										//retorna la lista completa
List<Comentario> comentarios=comentarioRepository.findAll();

		System.out.println("Ingrese al listado");
		
		
	return new ResponseEntity<List<Comentario>>(comentarios, HttpStatus.OK);	
	}
	
	
	/*Recibir un objeto comentario en formato json {}
	 * 						con la url /api/comentario
	 * Almacenar en la base de datos
	 * Retornar el nuevo comentario registrado
	 * Retornar el estado de la petición  ok si funciona.
	 * 								error si no funciona
	 * */
@PostMapping("comentario")	
public ResponseEntity<Comentario>
saveComentario(@RequestBody Comentario comentario){
	
	try {
		Comentario comentarioRetorno = comentarioRepository.save(comentario);
		//emite 200
		return new ResponseEntity<Comentario>(comentarioRetorno, HttpStatus.OK);
	} catch (Exception e) {
		
		e.printStackTrace();
		//si no funciona emite 500
return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);			
	}
	}

		/*
		 * Recibir el codigo de la entidad Comentario.
		 * por la URL /api/comentario
		 * Retornar un objeto comentario
		 * Retornar el status 200 si existe y 404 si no existe
		 * */
	

		@GetMapping("comentario/{codigo}")
		public ResponseEntity<Comentario> getById(@PathVariable Long codigo){
			System.out.println("Ingrese a consulta: "+codigo);
			Optional<Comentario> comentario =comentarioRepository.findById(codigo);
			
			if(comentario.isPresent()) {
				return new ResponseEntity<Comentario>(comentario.get(), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
	
		/*
		 * Recibir un codigo.
		 * por la url api/comentario  DELETE
		 * 
		 * Retornar en caso de eliminar el status 200
		 * Retornar en caso de no encontrar el status 404
		 * */
	
		@DeleteMapping("comentario/{codigo}")
		public ResponseEntity<Comentario> deleteById(@PathVariable Long codigo){
			System.out.println("Ingrese a ELIMINAR: "+codigo);
			Optional<Comentario> comentario =comentarioRepository.findById(codigo);
			
			if(comentario.isPresent()) {
				
				comentarioRepository.deleteById(codigo);
				
				return new ResponseEntity<>(HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
	
	

}
