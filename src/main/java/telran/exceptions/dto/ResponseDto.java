package telran.exceptions.dto;

public class ResponseDto {

    private String message;

    public ResponseDto() {
    }

    public ResponseDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
