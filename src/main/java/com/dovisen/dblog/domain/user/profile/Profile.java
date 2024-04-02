package com.dovisen.dblog.domain.user.profile;

import com.dovisen.dblog.domain.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.w3c.dom.Text;

import java.util.UUID;

@Entity
@Table(name = "TB_PROFILE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nickname;

    @Email
    private String email;

    @DateTimeFormat
    private String birthday;

    private Text biography;

    @OneToOne
    @JoinColumn(name="ID_USUARIO")
    private User user;



}
