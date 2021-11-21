package com.quackable.photo.model;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Photo {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "photoSetId", nullable = false)
    PhotoSet photoSet;
}
