package 도서.오브젝트.Chapter01.step03;

public class Bag {
    private Long amount; // 현금
    private Invitation invitation; // 초대장
    private Ticket ticket; // 티켓

    public Bag(long amount) { // 현금만 존재(관람객)
        this(null, amount);
    }
  
    public Bag(Invitation invitation, long amount) { // 초대장, 현금 존재(당첨자)
        this.invitation = invitation;
        this.amount = amount;
    }

    public Long hold(Ticket ticket) {
        if(hasInvitation()) {
            setTicket(ticket);
            return 0L;
        } else {
            setTicket(ticket);
            minusAmount(ticket.getFee());
            return ticket.getFee();
        }
    }

    public boolean hasInvitation() { // 초대장 소지 여부
        return invitation != null;
    }

    public boolean hasTicket() { // 티켓 소지 여부
        return ticket != null;
    }

    public void setTicket(Ticket ticket) { // 티켓 설정
        this.ticket = ticket;
    }

    public void minusAmount(Long amount) { // 현금 감소
        this.amount -= amount;
    }

    public void plusAmount(Long amount) { // 현금 증가
        this.amount += amount;
    }
}