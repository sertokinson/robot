package ru.sertok.robot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.sertok.robot.data.Type;

import javax.persistence.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "KEYBOARD")
public class KeyboardEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * PASSED - нажал
     * RELEASED - отпустил
     */
    @Column(name = "TYPE",nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    /**
     * Клавиша
     */
    @Column(name = "KEY", nullable = false)
    private String key;

    /**
     * порядок в котором было событие
     */
    @Column(name = "position", nullable = false)
    private int position;

    /**
     * Тест кейс к которому относится данное событие
     */
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "TEST_CASE_ID", nullable = false)
    private TestCaseEntity testCase;

    /**
     * Это переопределениме нужно чтоб получить только id тест-кейса
     * З.Ы.: Как это сделать через lombok не знаю
     * @return строку
     */
    @Override
    public String toString() {
        return "KeyboardEntity{" +
                "id=" + id +
                ", type=" + type +
                ", key='" + key + '\'' +
                ", position=" + position +
                ", testCaseId=" + testCase.getId() +
                '}';
    }
}
