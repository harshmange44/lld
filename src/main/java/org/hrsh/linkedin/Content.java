package org.hrsh.linkedin;

import java.math.BigInteger;

public class Content {
    private String id;
    private String text;
    private Media media;
    private Content parentContentId;
    private Member createdBy;
    private BigInteger noOfLikes;

    public String getId() {
        return id;
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
