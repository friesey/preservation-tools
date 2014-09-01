package pdfHackerTools;

public class PdfInfo {

    private String filename;
    private String creationSW;
    private boolean valid;
    private String version;
    private int errorGroup;
    private String errorMessage;

    public PdfInfo() {
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getCreationSW() {
        return creationSW;
    }

    public void setCreationSW(String creationSW) {
        this.creationSW = creationSW;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getErrorGroup() {
        return errorGroup;
    }

    public void setErrorGroup(int errorGroup) {
        this.errorGroup = errorGroup;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
