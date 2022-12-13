package steammachinist.stockmarket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import steammachinist.stockmarket.entitymodel.Position;
import steammachinist.stockmarket.entitymodel.PositionId;
import steammachinist.stockmarket.repository.PositionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionService {
    private final PositionRepository positionRepository;

    public Position findById(PositionId id) throws Exception {
        return positionRepository.findById(id)
                .orElseThrow(() -> new Exception("Stock not found: id = " + id));
    }

    public List<Position> getAllPositions() {
        return positionRepository.findAll();
    }

    public void addPosition(Position position) {
        positionRepository.save(position);
    }

    public void addPositions(List<Position> positions) {
        positionRepository.saveAll(positions);
    }
}
