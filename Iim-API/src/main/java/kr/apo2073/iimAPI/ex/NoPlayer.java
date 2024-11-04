package kr.apo2073.iimAPI.ex;

public class NoPlayer extends RuntimeException {
    public NoPlayer() {
        super("플레이어가 존재하지 않습니다");
    }
}
