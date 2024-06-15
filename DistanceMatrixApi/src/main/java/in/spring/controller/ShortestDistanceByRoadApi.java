package in.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.spring.dto.ApiResponse;
import in.spring.entity.User;
import in.spring.service.DistanceService;

@RestController
public class ShortestDistanceByRoadApi {
	
	@Autowired
	private DistanceService service;
	
	@PostMapping("/save")
   public ResponseEntity<String> saveUser(@RequestBody User user) {
	   boolean flag = service.saveUser(user);
	   if(flag) {
		   return ResponseEntity.status(201).body("Suces");
	   }
	   return ResponseEntity.status(505).body("Falied");
	   
   }
	@GetMapping("/get")
  public ResponseEntity<ApiResponse> getUser(@RequestParam("address") String address) {
	  
	  ApiResponse nearestUser = service.getNearestUser(address);
	  return ResponseEntity.status(200).body(nearestUser);
	  
	   
   }
   
}
