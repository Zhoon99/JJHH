package spring.assignment.jjhh.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Long pCommentId;

    @Column(nullable = false)
    private Integer depth;

}
