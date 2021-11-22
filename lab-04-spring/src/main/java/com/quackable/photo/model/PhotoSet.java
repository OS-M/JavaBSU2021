package com.quackable.photo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class PhotoSet {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Integer id;
    String name;
    String description;
    String location;
    Date date;
    @JsonIgnore
    @OneToMany(mappedBy = "photoSet")
    List<Photo> photos;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        PhotoSet photoSet = (PhotoSet) o;
        return id != null && Objects.equals(id, photoSet.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
