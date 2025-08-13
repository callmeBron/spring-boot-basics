package entelect.training.incubator.model;

import lombok.Data;

@Data
public class BookedFlightSearchRequest {
    private SearchType searchType;
    private Integer customerId;
    private Integer flightId;
    private String referenceNumber;
}
