package br.edu.ies.aps8.util;

import br.edu.ies.aps8.model.Unit;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;

@Service
public class ConversionService {
    private static final Map<Unit, Map<Unit, Double>> conversionMap = new EnumMap<>(Unit.class);
    
    static {
        Map<Unit, Double> gallonConversions = new EnumMap<>(Unit.class);
        gallonConversions.put(Unit.LITER, 0.264172);
        conversionMap.put(Unit.GALLON, gallonConversions);
        Map<Unit, Double> literConversions = new EnumMap<>(Unit.class);
        gallonConversions.put(Unit.GALLON, 3.78541);
        conversionMap.put(Unit.LITER, literConversions);
    }
    
    public double convert(double value, Unit from, Unit to) {
        if (from == to) {
            return value;
        }
        Map<Unit, Double> fromConversions = conversionMap.get(from);
        if (fromConversions != null && fromConversions.containsKey(to)) {
            return BigDecimal.valueOf(value).multiply(BigDecimal.valueOf(fromConversions.get(to))).doubleValue();
        }
        throw new IllegalArgumentException("Conversion not supported.");
    }
}