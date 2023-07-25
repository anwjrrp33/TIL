package 도서.오브젝트.Chapter11.eternity.billing.step05;

import 도서.오브젝트.Chapter11.eternity.money.Money;

public interface RatePolicy {
    Money calculateFee(Phone phone);
}
