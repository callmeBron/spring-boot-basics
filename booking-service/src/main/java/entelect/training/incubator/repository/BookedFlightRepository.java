package entelect.training.incubator.repository;

import entelect.training.incubator.model.BookedFlight;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookedFlightRepository
        extends CrudRepository<BookedFlight, Integer> {
    Optional<List<BookedFlight>> findFlightsByCustomerID(Integer customerID);
    Optional<List<BookedFlight>> findFlightsByReferenceNumber(String referenceNumber);

    @Query(value = "SELECT s FROM BookedFlight s WHERE s.flightID = ?1 AND s.customerID = ?1")
    Optional<BookedFlight> findFlight(BookedFlight bookedFlight);

    @Query(value = "SELECT s FROM BookedFlight s WHERE s.flightID = ?1")
    Optional<BookedFlight> findByFlightId(Integer FlightID);
}