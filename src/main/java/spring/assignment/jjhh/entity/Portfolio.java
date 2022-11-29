package spring.assignment.jjhh.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @Column(length = 10, nullable = false)
    private String startDate;

    @Column(length = 10, nullable = false)
    private String lastDate;

    @Lob
    private String readme;

    @Column(length = 3, nullable = false)
    private String disclosure; //공개 여부

    @Column(nullable = false)
    private Integer views;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "portfolio")
    private List<File> fileList = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio")
    private List<TechStack> techStackList = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio")
    private List<Team> teamList = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio")
    private List<Comment> commentList = new ArrayList<>();
}
