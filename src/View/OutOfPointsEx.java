package View;

class OutOfPointsEx extends Exception{
    String getError() {
        return error;
    }

    void setError(String error) {
        this.error = error;
    }

    private String error;

}
