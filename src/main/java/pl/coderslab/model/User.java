package pl.coderslab.model;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String email, userName, password, creationDate, lastEdited;
    private boolean isAdmin = false;
}

