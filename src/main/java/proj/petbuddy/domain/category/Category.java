package proj.petbuddy.domain.category;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cateName")
    private String cateName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @Column(name = "depth")
    private Long depth;

    @OneToMany(mappedBy = "parent")
    public List<Category> children = new ArrayList<>();


    @Builder
    public Category(Long id, String cateName, Long depth) {
        this.id = id;
        this.cateName = cateName;
        this.depth = depth;
    }


    public Category() {

    }

    public Category(String cateName, Long depth) {
        this.cateName = cateName;
        this.depth = depth;
    }

    public Category(String cateName, Long parentId, Long depth) {
        this.cateName = cateName;
        if (parentId != null) {
            this.parent = new Category();
            this.parent.setId(parentId);
        }
        this.depth = depth;
    }
}
