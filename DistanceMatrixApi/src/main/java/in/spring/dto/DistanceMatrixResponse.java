package in.spring.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class DistanceMatrixResponse {
	
	private List<DistanceMatrixRow> rows;

}
