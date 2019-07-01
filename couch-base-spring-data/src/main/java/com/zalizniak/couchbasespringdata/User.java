package com.zalizniak.couchbasespringdata;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import org.springframework.data.annotation.*;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Document
public class User {

    @Id
    //@GeneratedValue(strategy = GenerationStrategy.USE_ATTRIBUTES, delimiter = ".")
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    private String id;

    @Size(min = 2)
    @Field
    //@IdPrefix(order = 0)
    private String firstname;

    @Field
    //@IdSuffix(order = 0)
    private String lastname;

    @Field
    private List<String> nicknames;

    @Field
    private String email;

    @Field
    private Date birthday;

    @Version
    private long version;

    @CreatedBy
    private String creator; // Auditing

    @LastModifiedBy
    private String lastModifiedBy; // Auditing

    @LastModifiedDate
    private Date lastModification; // Auditing

    @CreatedDate
    private Date creationDate; // Auditing

    public String getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public List<String> getNicknames() {
        return nicknames;
    }

    public void setNicknames(List<String> nicknames) {
        this.nicknames = nicknames;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModification() {
        return lastModification;
    }

    public void setLastModification(Date lastModification) {
        this.lastModification = lastModification;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}