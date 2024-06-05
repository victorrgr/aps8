package br.edu.ies.aps8.api;

import br.edu.ies.aps8.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {
    private final TripRepository tripRepository;

    @GetMapping
    public List<Map<String, Object>> getReport() {
        return tripRepository.findAll().stream()
                .map(trip -> {
                    Map<String, Object> report = new LinkedHashMap<>();
                    double fuelEfficiency = trip.getFuelAmount() / trip.getDistance();
                    report.put("fuelEfficiency", fuelEfficiency);
                    return report;
                })
                .toList();
    }

    @GetMapping("/week")
    public String getHello() {
        return "Report Week";
    }
}
