package entelect.training.incubator.service;

import entelect.training.incubator.model.BookedFlight;
import entelect.training.incubator.model.BookedFlightSearchRequest;
import entelect.training.incubator.model.SearchType;
import entelect.training.incubator.repository.BookedFlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

@Service
public class BookedFlightService {
    // get the repository here
    private final BookedFlightRepository bookedFlightRepository;

    public BookedFlightService(BookedFlightRepository bookedFlightRepository) {
        this.bookedFlightRepository = bookedFlightRepository;
    }

    public BookedFlight getBookingByFlightID(Integer flightID) {
        return bookedFlightRepository.findByFlightId(flightID).orElse(null);
    }

    public BookedFlight createNewFlightBooking(BookedFlight bookedFlight) {
        return bookedFlightRepository.save(bookedFlight);
    }

    private Boolean flightExistsForUser(BookedFlight bookedFlight) {
        return bookedFlightRepository.findFlight(bookedFlight).isPresent();
    }

    public List<BookedFlight> getBookingsBySearch(BookedFlightSearchRequest searchRequest) {
        Map<SearchType, Supplier<Optional<List<BookedFlight>>>> searchStrategies = new HashMap<>();
        searchStrategies.put(SearchType.CUSTOMERID_SEARCH, () -> bookedFlightRepository.findFlightsByCustomerID(searchRequest.getCustomerId()));
        searchStrategies.put(SearchType.REFERNCENUMEBR_SEARCH,() -> bookedFlightRepository.findFlightsByReferenceNumber(searchRequest.getReferenceNumber()));

        Optional<List<BookedFlight>> bookedFlightsOptional = searchStrategies.get(searchRequest.getSearchType()).get();

        return bookedFlightsOptional.orElse(null);
    }
}