package org.hrsh.linkedin;

import java.math.BigInteger;
import java.util.UUID;

public class Content {
    private String id;
    private String text;
    private Media media;
    private Content parentContentId;
    private Member createdBy;
    private BigInteger noOfLikes;

    public Content() {
        this.id = UUID.randomUUID().toString();
        this.noOfLikes = BigInteger.ZERO;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public Content getParentContentId() {
        return parentContentId;
    }

    public void setParentContentId(Content parentContentId) {
        this.parentContentId = parentContentId;
    }

    public Member getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Member createdBy) {
        this.createdBy = createdBy;
    }

    public BigInteger getNoOfLikes() {
        return noOfLikes;
    }

    public void setNoOfLikes(BigInteger noOfLikes) {
        this.noOfLikes = noOfLikes;
    }
}
