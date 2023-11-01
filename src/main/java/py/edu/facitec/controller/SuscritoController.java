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

import py.edu.facitec.model.Suscrito;
import py.edu.facitec.repository.SuscritoRepository;

//Arquitectura REST
//Representational State Transfer
//Petición sin gestión de estado en el servidor
@RestController
@RequestMapping("api/") //afecta a todos los metodos
public class SuscritoController {
	@Autowired
	private SuscritoRepository suscritoRepository;
	
	
	// retornara una lista de suscritos en formato json
	// retornar un estado de la petición
	// iniciar con la petición /api/suscritos (cli-ser)
	// retornar del ser al cliente un json [] (ser-cli)
	@GetMapping("suscritos")
	public ResponseEntity<List<Suscrito>> getSuscritos(){
										//retorna la lista completa
List<Suscrito> suscritos=suscritoRepository.findAll();

		System.out.println("Ingrese al listado");
		
		
	return new ResponseEntity<List<Suscrito>>(suscritos, HttpStatus.OK);	
	}
	
	
	/*Recibir un objeto suscrito en formato json {}
	 * 						con la url /api/suscrito
	 * Almacenar en la base de datos
	 * Retornar el nuevo suscrito registrado
	 * Retornar el estado de la petición  ok si funciona.
	 * 								error si no funciona
	 * */
@PostMapping("suscrito")	
public ResponseEntity<Suscrito>
saveSuscrito(@RequestBody Suscrito suscrito){
	
	try {
		Suscrito suscritoRetorno = suscritoRepository.save(suscrito);
		//emite 200
		return new ResponseEntity<Suscrito>(suscritoRetorno, HttpStatus.OK);
	} catch (Exception e) {
		
		e.printStackTrace();
		//si no funciona emite 500
return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);			
	}
	}

		/*
		 * Recibir el codigo de la entidad Suscrito.
		 * por la URL /api/suscrito
		 * Retornar un objeto suscrito
		 * Retornar el status 200 si existe y 404 si no existe
		 * */
	

		@GetMapping("suscrito/{codigo}")
		public ResponseEntity<Suscrito> getById(@PathVariable Long codigo){
			System.out.println("Ingrese a consulta: "+codigo);
			Optional<Suscrito> suscrito =suscritoRepository.findById(codigo);
			
			if(suscrito.isPresent()) {
				return new ResponseEntity<Suscrito>(suscrito.get(), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
	
		/*
		 * Recibir un codigo.
		 * por la url api/suscrito  DELETE
		 * 
		 * Retornar en caso de eliminar el status 200
		 * Retornar en caso de no encontrar el status 404
		 * */
	
		@DeleteMapping("suscrito/{codigo}")
		public ResponseEntity<Suscrito> deleteById(@PathVariable Long codigo){
			System.out.println("Ingrese a ELIMINAR: "+codigo);
			Optional<Suscrito> suscrito =suscritoRepository.findById(codigo);
			
			if(suscrito.isPresent()) {
				
				suscritoRepository.deleteById(codigo);
				
				return new ResponseEntity<>(HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
	
	

}
