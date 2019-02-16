package com.zalizniak.awsdynamodbdao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.*;

import java.util.Objects;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBDocument
public class UserNote {

    @EqualsAndHashCode.Exclude
    private Long noteId;

    private String noteText;

    @DynamoDBAttribute(attributeName = "note_id")
    public Long getNoteId() {
        return noteId;
    }

    @DynamoDBAttribute(attributeName = "note_text")
    public String getNoteText() {
        return noteText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserNote userNote = (UserNote) o;
        return Objects.equals(noteId, userNote.noteId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(noteId);
    }
}