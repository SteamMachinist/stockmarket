package steammachinist.stockmarket.service.dataservice;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import steammachinist.stockmarket.entitymodel.Position;
import steammachinist.stockmarket.entitymodel.PositionId;
import steammachinist.stockmarket.entitymodel.Stock;
import steammachinist.stockmarket.entitymodel.User;
import steammachinist.stockmarket.repository.PositionRepository;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionService {
    private final PositionRepository positionRepository;

    public Position findById(PositionId id) throws Exception {
        return positionRepository.findById(id)
                .orElseThrow(() -> new Exception("Position not found: id = " + id));
    }

    public Position getUserPositionOnStock(User user, Stock stock) {
        try {
            return this.findById(new PositionId(stock, user));
        } catch (Exception e) {
            return null;
        }
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

    public void deletePosition(Position position) {
        positionRepository.delete(position);
    }

    public long count() {
        return positionRepository.count();
    }

    public List<Position> getUserPositions(User user) {
        return positionRepository.getPositionsByPositionId_User(user);
    }

    public Page<Position> getPaginatedUserPositions(User user, Pageable pageable) {
        List<Position> positions = positionRepository.getPositionsByPositionId_User(user);
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Position> list;

        if (positions.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, positions.size());
            list = positions.subList(startItem, toIndex);
        }

        Page<Position> positionsPage
                = new PageImpl<Position>(list, PageRequest.of(currentPage, pageSize), positions.size());

        return positionsPage;
    }

    public List<Stock> getUserStocks(User user) {
        return positionRepository.getPositionsByPositionId_User(user)
                .stream()
                .map(position -> position.getPositionId().getStock())
                .toList();
    }
}
