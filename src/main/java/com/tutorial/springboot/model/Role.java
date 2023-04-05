package com.tutorial.springboot.model;

import com.tutorial.springboot.enums.ERole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "roles")
@ToString
public class Role {
    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @Column(unique = true, nullable = false, updatable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private ERole eRole;

}
