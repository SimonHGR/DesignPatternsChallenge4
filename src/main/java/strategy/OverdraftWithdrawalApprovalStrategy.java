package strategy;

import java.time.LocalDate;
import java.util.List;

public class OverdraftWithdrawalApprovalStrategy implements WithdrawalApprovalStrategy {
  private final int minimumPermittedBalance;

  private OverdraftWithdrawalApprovalStrategy(int mayBorrow) {
    minimumPermittedBalance = -mayBorrow;
  }

  public static WithdrawalApprovalStrategy borrowing(int mayBorrow) {
    return new OverdraftWithdrawalApprovalStrategy(mayBorrow);
  }

  @Override
  public boolean grantWithdrawal(int balance, int amount, LocalDate today, List<Transaction> history) {
    // amount here is a *transaction* amount, so expected to be negative
    assert amount < 0 : "withdrawal is a negative value transaction";
    return balance + amount >= minimumPermittedBalance;
  }
}
