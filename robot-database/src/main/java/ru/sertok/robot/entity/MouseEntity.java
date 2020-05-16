package ru.sertok.robot.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.sertok.robot.data.enumerate.Type;

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
     * порядок в котором было событие
     */
    @Column(name = "position", nullable = false)
    private int position;

    /**
     * Признак что есть скриншот у этого события
     */
    @Column(name = "screenshot", nullable = false)
    private boolean screenshot;

    /**
     * Тест кейс к которому относится данное событие
     */
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "TEST_CASE_ID")
    private TestCaseEntity testCase;

    /**
     * Это переопределениме нужно чтоб получить только id тест-кейса
     * З.Ы.: Как это сделать через lombok не знаю
     * @return строку
     */
    @Override
    public String toString() {
        return "MouseEntity{" +
                "id=" + id +
                ", type=" + type +
                ", x=" + x +
                ", y=" + y +
                ", time=" + time +
                ", position=" + position +
                ", screenshot=" + screenshot +
                ", testCaseId=" + testCase.getId() +
                '}';
    }
}
