package kr.apo2073.iimAPI.ex;

public class NoItem extends RuntimeException {
    public NoItem() {
        super("아이템이 존재하지 않습니다");
    }
}
