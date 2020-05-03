package ru.sertok.robot.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SCREEN_SHOT")
public class ScreenShotEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "X")
    private int x;

    @Column(name = "Y")
    private int y;

    @Column(name = "WIDTH")
    private int width;

    @Column(name = "HEIGHT")
    private int height;

    @Column(name = "SIZE")
    private Integer size;

    /**
     * порядок в катором было событие
     */
    @Column(name = "position", nullable = false)
    private int position;

    /**
     * время отсчета от старта
     */
    @Column(name = "TIME")
    private Integer time;

    /**
     * Тест кейс к которому относится данное событие
     */
    @ManyToOne
    @JoinColumn(name = "TEST_CASE_ID")
    private TestCaseEntity testCase;
}
