package Controller;

public class NoPathEx extends Exception{
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    private String error;

}