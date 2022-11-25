package spring.assignment.jjhh.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Portfolio extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_id")
    private Long id;

    @Column(length = 50, nullable = false)
    private String projectName;

    @Column(nullable = false)
    private String introduce;

    @Column(length = 50, nullable = false)
    private String duration;

    @Lob
    private String readme;

    @Column(length = 3, nullable = false)
    private String disclosure; //공개 여부

    @Column(nullable = false)
    private Integer views;
}
