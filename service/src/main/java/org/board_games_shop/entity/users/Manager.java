package org.board_games_shop.entity.users;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import org.board_games_shop.entity.Role;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("MANAGER")
public class Manager extends User {

    private String jobTitle;

    @Builder
    public Manager(String login, String password, String firstname, String lastname, String phone, Role role, LocalDate birthDate, LocalDate registrationDate, String jobTitle) {
        super(login, password, firstname, lastname, phone, role, birthDate, registrationDate);
        this.jobTitle = jobTitle;
    }
}
