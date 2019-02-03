package com.zalizniak.postgres;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author aillusions
 */
@Getter
@Setter
@Entity()
@Table(name = "temp_entry")
public class TempEntry implements Serializable {

    @Id
    @Column(name = "entry_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "date_created", nullable = false)
    private Date dateCreated;
}
