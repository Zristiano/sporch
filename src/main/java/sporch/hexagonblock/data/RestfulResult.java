package sporch.hexagonblock.data;

public class RestfulResult<Result> {
    private int errorCode;
    private String message;
    private Result result;

    public RestfulResult(int err, String msg, Result r){
        errorCode = err;
        message = msg;
        result = r;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
