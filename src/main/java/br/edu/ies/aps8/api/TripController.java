package br.edu.ies.aps8.api;

import br.edu.ies.aps8.dto.trip.TripRequest;
import br.edu.ies.aps8.dto.trip.TripResponse;
import br.edu.ies.aps8.mapper.TripMapper;
import br.edu.ies.aps8.model.Trip;
import br.edu.ies.aps8.repository.TripRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
@RequiredArgsConstructor
public class TripController {
    private final TripRepository tripRepository;
    private final TripMapper tripMapper;

    @GetMapping("/{id}")
    public TripResponse findById(@PathVariable Long id) {
        return tripRepository.findById(id)
                .map(tripMapper::mapToResponse)
                .orElseThrow(() -> new IllegalArgumentException("Trip with id '%s' not found".formatted(id)));
    }

    @GetMapping
    public List<TripResponse> findAll() {
        return tripRepository.findAll().stream()
                .map(tripMapper::mapToResponse)
                .toList();
    }

    @PostMapping
    public TripResponse save(@Valid @RequestBody TripRequest tripRequest) {
        Trip trip = tripMapper.mapToModel(tripRequest);
        trip = tripRepository.save(trip);
        return tripMapper.mapToResponse(trip);
    }

    @PostMapping("/batch")
    public List<TripResponse> saveBatch(@RequestBody List<TripRequest> tripRequests) {
        return tripRequests.stream()
                .map(this::save)
                .toList();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Trip trip = tripRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Trip with id '%s' not found".formatted(id)));
        tripRepository.delete(trip);
    }

}
