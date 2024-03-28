package com.dovisen.dblog.domain.Journey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class JourneyNodeService {
    private final JourneyNodeRepository journeyNodeRepository;

    public JourneyNodeService(JourneyNodeRepository journeyNodeRepository){
        this.journeyNodeRepository = journeyNodeRepository;
    }

    public JourneyNode save(JourneyNode node) {
        return journeyNodeRepository.save(node);
    }

    public List<JourneyNode> findAll() {
        return journeyNodeRepository.findAll();
    }

    public JourneyNode findById(UUID id) {
        return journeyNodeRepository.findById(id).orElse(null);
    }

    public void deleteById(UUID id) {
        journeyNodeRepository.deleteById(id);
    }
}
