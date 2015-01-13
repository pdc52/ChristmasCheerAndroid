package philipcorriveau.com.christmascheer.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by philipcorriveau on 12/12/14.
 */
public class Cheer {

    @Expose
    private String alert;
    @Expose
    private String badge;
    @Expose
    private String fromInstallationId;
    @Expose
    private String fromLocation;
    @Expose
    private String fromName;
    @Expose
    private String fromUserId;
    @Expose
    private Boolean isResponse;
    @Expose
    private String originalNoteId;
    @SerializedName("push_hash")
    @Expose
    private String pushHash;
    @Expose
    private String sound;
    @Expose
    private String title;
    @Expose
    private String toInstallationId;

    /**
     * @return The alert
     */
    public String getAlert() {
        return alert;
    }

    /**
     * @param alert The alert
     */
    public void setAlert(String alert) {
        this.alert = alert;
    }

    /**
     * @return The badge
     */
    public String getBadge() {
        return badge;
    }

    /**
     * @param badge The badge
     */
    public void setBadge(String badge) {
        this.badge = badge;
    }

    /**
     * @return The fromInstallationId
     */
    public String getFromInstallationId() {
        return fromInstallationId;
    }

    /**
     * @param fromInstallationId The fromInstallationId
     */
    public void setFromInstallationId(String fromInstallationId) {
        this.fromInstallationId = fromInstallationId;
    }

    /**
     * @return The fromLocation
     */
    public String getFromLocation() {
        return fromLocation;
    }

    /**
     * @param fromLocation The fromLocation
     */
    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    /**
     * @return The fromName
     */
    public String getFromName() {
        return fromName;
    }

    /**
     * @param fromName The fromName
     */
    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    /**
     * @return The fromUserId
     */
    public String getFromUserId() {
        return fromUserId;
    }

    /**
     * @param fromUserId The fromUserId
     */
    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    /**
     * @return The isResponse
     */
    public Boolean getIsResponse() {
        return isResponse;
    }

    /**
     * @param isResponse The isResponse
     */
    public void setIsResponse(Boolean isResponse) {
        this.isResponse = isResponse;
    }

    /**
     * @return The originalNoteId
     */
    public String getOriginalNoteId() {
        return originalNoteId;
    }

    /**
     * @param originalNoteId The originalNoteId
     */
    public void setOriginalNoteId(String originalNoteId) {
        this.originalNoteId = originalNoteId;
    }

    /**
     * @return The pushHash
     */
    public String getPushHash() {
        return pushHash;
    }

    /**
     * @param pushHash The push_hash
     */
    public void setPushHash(String pushHash) {
        this.pushHash = pushHash;
    }

    /**
     * @return The sound
     */
    public String getSound() {
        return sound;
    }

    /**
     * @param sound The sound
     */
    public void setSound(String sound) {
        this.sound = sound;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The toInstallationId
     */
    public String getToInstallationId() {
        return toInstallationId;
    }

    /**
     * @param toInstallationId The toInstallationId
     */
    public void setToInstallationId(String toInstallationId) {
        this.toInstallationId = toInstallationId;
    }
}
