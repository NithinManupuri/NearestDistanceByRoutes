package in.spring.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import in.spring.dto.ApiResponse;
import in.spring.dto.DistanceMatrixResponse;
import in.spring.dto.DistanceMatrixRow;
import in.spring.entity.User;
import in.spring.repo.UserRepo;

@Service
public class DistanceService {

	
	@Autowired
	private UserRepo repo;
	
	public ApiResponse  getNearestUser(String address) {
		List<User> listOfUsers = repo.findAll();
		String apiKey="****************************";
	    ApiResponse res=new ApiResponse();
	    String origins=listOfUsers.stream().map(user -> user.getAddress()+ ","+user.getArea()+","+user.getPincode()).collect(Collectors.joining("|"));
		
	    String url = "https://maps.googleapis.com/maps/api/distancematrix/json"
                + "?units=metric&origins=" + origins
                + "&destinations=" + address
                + "&key="+apiKey;
	    RestTemplate restTemplate=new RestTemplate();
	   DistanceMatrixResponse response = restTemplate.getForObject(url,DistanceMatrixResponse.class);
	   if (response != null && response.getRows() != null && !response.getRows().isEmpty()) {
           DistanceMatrixRow shortestRow = response.getRows().stream()
                   .min((row1, row2) -> Integer.compare(
                           row1.getElements().get(0).getDistance().getValue(),
                           row2.getElements().get(0).getDistance().getValue()))
                   .orElse(null);

           if (shortestRow != null) {
               int shortestDistance = shortestRow.getElements().get(0).getDistance().getValue();
               int indexOfShortest = response.getRows().indexOf(shortestRow);
               int nearestUserId = listOfUsers.get(indexOfShortest).getUserId();
               double = shortestDistance/1000;
               String inKm=double+"km";

               res.setUserId(nearestUserId);
               res.setDistance(inKm);
           } else {
               res.setUserId(null);
               res.setDistance("Not Found");
           }
       } else {
           res.setUserId(null);
           res.setDistance("Not Places");
       }

       return res;
	
         		
	}
	
	
	public boolean saveUser(User user) {
		try {
			
	
		repo.save(user);
		return true;
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
}
