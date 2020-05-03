package ru.sertok.robot.entity;

import lombok.*;
import ru.sertok.robot.data.Type;

import javax.persistence.*;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MOUSE")
public class MouseEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * PASSED - нажал
     * RELEASED - отпустил
     */
    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private Type type;

    /**
     * Координата мыши по x
     */
    @Column(name = "X")
    private int x;

    /**
     * Координата мыши по y
     */
    @Column(name = "Y")
    private int y;

    /**
     * время отсчета от старта
     */
    @Column(name = "TIME")
    private Integer time;

    /**
     * порядок в катором было событие
     */
    @Column(name = "position", nullable = false)
    private int position;

    /**
     * Тест кейс к которому относится данное событие
     */
    @ManyToOne
    @JoinColumn(name = "TEST_CASE_ID")
    private TestCaseEntity testCase;
}
