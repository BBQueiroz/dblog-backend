package com.dovisen.dblog.domain.journey;

import com.dovisen.dblog.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

import java.util.List;

@Entity
@Table(name = "TB_JOURNEY")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JourneyNode {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "root_id")
    private JourneyNode root;

    private String text;

    @ManyToOne
    @JoinColumn(name= "user_id")
    private User user;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<JourneyNode> children;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private JourneyNode parent;

    public JourneyNode(JourneyDTO journeyDTO) {
        this.user = journeyDTO.user();
        this.root = journeyDTO.root();
        this.text = journeyDTO.text();
        this.children = journeyDTO.children();
        this.parent = journeyDTO.parent();
    }
}
