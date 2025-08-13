package entelect.training.incubator.controller;

import entelect.training.incubator.model.BookedFlight;
import entelect.training.incubator.model.BookedFlightSearchRequest;
import entelect.training.incubator.service.BookedFlightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/molo-air/bookings/")
public class BookingServiceController {
    private final Logger LOGGER = LoggerFactory.getLogger(BookingServiceController.class);
    private final BookedFlightService bookedFlightService;

    public BookingServiceController(BookedFlightService bookedFlightService) {
        this.bookedFlightService = bookedFlightService;
    }

    // retrieve booking by an ID
    @GetMapping("/{flightID}")
    public ResponseEntity<BookedFlight> getBookingsByFlightID(@PathVariable Integer flightID){
        LOGGER.info("Fetching Booking by flight ID request={}", flightID);
        BookedFlight bookedFlight = bookedFlightService.getBookingByFlightID(flightID);

        if (bookedFlight != null) {
            LOGGER.info("Found Booking by flightID");
            return new ResponseEntity<>(bookedFlight, HttpStatus.OK);
        }

        LOGGER.info("Booking by flightID Not found");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // make a new booking
   @PostMapping("/bookFlight")
    public ResponseEntity<BookedFlight> makeABooking(@RequestBody BookedFlight createdFlight) {
       LOGGER.info("Processing booking creation request");
       final BookedFlight bookedFlight = bookedFlightService.createNewFlightBooking(createdFlight);
       LOGGER.info("Booking Created");
        return new ResponseEntity<>(bookedFlight, HttpStatus.OK);
    }

    // get all bookings for a customer id or reference number
    @PostMapping("/search")
    public ResponseEntity<?> getBookingsBySearch(@RequestBody BookedFlightSearchRequest searchRequest) {
        LOGGER.info("Processing booking search request for request {}", searchRequest);
        List<BookedFlight> bookedFlight = bookedFlightService.getBookingsBySearch(searchRequest);
        if (bookedFlight != null) {
            LOGGER.trace("Bookings found");
            return ResponseEntity.ok().body(bookedFlight);
        }
        LOGGER.info("Bookings not found");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
 }
