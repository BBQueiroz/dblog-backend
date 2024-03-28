package com.dovisen.dblog.domain.Journey;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JourneyNode {

    @UUID
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String text;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<JourneyNode> children = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private JourneyNode parent;

}
