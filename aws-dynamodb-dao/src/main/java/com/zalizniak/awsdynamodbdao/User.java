package com.zalizniak.awsdynamodbdao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@NoArgsConstructor
@DynamoDBTable(tableName = "myDynamoDBTable")
@EqualsAndHashCode()
public class User {

    @EqualsAndHashCode.Include
    private String id;

    @EqualsAndHashCode.Exclude
    private String firstName;

    @EqualsAndHashCode.Exclude
    private String lastName;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @DynamoDBHashKey(attributeName = "ArtistId")
    @DynamoDBAutoGeneratedKey
    public String getId() {
        return id;
    }

    @DynamoDBAttribute(attributeName = "Concert")
    public String getFirstName() {
        return firstName;
    }

    @DynamoDBAttribute(attributeName = "TicketSales")
    public String getLastName() {
        return lastName;
    }

}