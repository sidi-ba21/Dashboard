package Spauth.security.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "widgets")
public class Widgets {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String url;

    @Column
    private Long userId;

    @Column
    private String value;

    public Widgets(String url, Long userId, String value) {
        this.url = url;
        this.userId = userId;
        this.value = value;
    }

    protected Widgets(){}

    public Long getId(){return this.id;}

    public String getUrl(){return this.url;}

    public String getValue(){return this.value;}

    public void setValue(String value){this.value = value;}

    public Long getUserId(){return this.userId;}

    public void setId(Long id){this.id = id;}

    public void setUserId(Long userId){this.userId = userId;}

    public void setPassword(String url){this.url = url;}
}