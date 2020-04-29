package be.pxl.student.entity;



import javax.persistence.*;

@Entity
@NamedQueries(
        {
                @NamedQuery(name = "findLabelByName", query = "SELECT l FROM Label l WHERE l.name = :name"),
                @NamedQuery(name = "findAllLabels", query = "SELECT l FROM Label l")
        }
)
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Label(){
        //JPA only
    }

    public Label (String name){
        setName(name);
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
