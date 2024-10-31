package kr.apo2073.itemImage.ex;

public class NoItem extends RuntimeException {
    public NoItem() {
        super("Iim에 아이템이 설정되어 있지 않습니다");
    }
}
