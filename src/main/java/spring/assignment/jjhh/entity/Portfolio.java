package spring.assignment.jjhh.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = {"account","fileList","techStackList","teamList","commentList"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Portfolio extends BaseEntity implements Serializable {

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

    private Integer views;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "portfolio", orphanRemoval = true)
    private List<File> fileList = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio", orphanRemoval = true)
    private List<TechStack> techStackList = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio", orphanRemoval = true)
    private List<Team> teamList = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio", orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    public void registInit(Integer views, Account account) {
        this.views = views;
        this.account = account;
    }
}
