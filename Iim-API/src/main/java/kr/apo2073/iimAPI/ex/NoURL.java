package kr.apo2073.iimAPI.ex;

public class NoURL extends RuntimeException {
    public NoURL() {
        super("URL이 존재하지 않습니다");
    }
}
