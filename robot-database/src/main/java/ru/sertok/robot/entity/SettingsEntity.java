package ru.sertok.robot.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SETTINGS",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"HOST"})})
public class SettingsEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * host приложения
     */
    @Column(name = "HOST")
    private String host;

}
