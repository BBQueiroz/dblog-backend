package com.dovisen.dblog.domain.journey;

import com.dovisen.dblog.domain.user.User;
import com.dovisen.dblog.domain.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class JourneyNodeService {
    private final JourneyNodeRepository journeyNodeRepository;

    private final UserRepository userRepository;

    public JourneyNodeService(JourneyNodeRepository journeyNodeRepository, UserRepository userRepository){
        this.journeyNodeRepository = journeyNodeRepository;
        this.userRepository = userRepository;
    }

    public List<JourneyNode> getAllJourneyRoots(){
        return this.journeyNodeRepository.findAll().stream().filter(journeyNode -> journeyNode.getRoot() == null).collect(Collectors.toList());
    }

    public List<JourneyNode> getOneJourney(UUID id){
        return this.journeyNodeRepository.findAll().stream().filter(journeyNode -> journeyNode.getId() == id && journeyNode.getRoot().getId() == id).collect(Collectors.toList());
    }

    public JourneyNode save(JourneyDTO journeyDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JourneyNode journeyNode = new JourneyNode(journeyDTO);
        journeyNode.setUser((User) userRepository.findByLogin(authentication.getName()));
        return journeyNodeRepository.save(journeyNode);
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
