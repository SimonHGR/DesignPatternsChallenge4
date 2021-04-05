package pooling;

import org.junit.Assert;
import org.junit.Test;
import strategy.PooledOverdraftWithdrawalApprovalStrategy;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

public class TestPooling {
  @Test
  public void noAccessibleConstructors() {
    Class<?> cls = PooledOverdraftWithdrawalApprovalStrategy.class;
    Constructor<?> [] constructors = cls.getDeclaredConstructors();
    for (Constructor<?> cons : constructors) {
      if ((cons.getModifiers() & Modifier.PRIVATE) != Modifier.PRIVATE) {
        throw new RuntimeException("Non-private constructor " + cons);
      }
    }
  }

  @Test
  public void overdraftsShouldBeDivisibleBy500() {
    PooledOverdraftWithdrawalApprovalStrategy.borrowing(500);
    PooledOverdraftWithdrawalApprovalStrategy.borrowing(1000);
    PooledOverdraftWithdrawalApprovalStrategy.borrowing(1500);
  }

  @Test(expected = Exception.class)
  public void overdraftsShouldOnlyBeDivisibleBy500() {
    PooledOverdraftWithdrawalApprovalStrategy.borrowing(600);
  }

  @Test
  public void differentOverdraftsAreDifferentObjects() {
    var fiveHundred = PooledOverdraftWithdrawalApprovalStrategy.borrowing(500);
    var oneThousand = PooledOverdraftWithdrawalApprovalStrategy.borrowing(1000);
    var fifteenHundred = PooledOverdraftWithdrawalApprovalStrategy.borrowing(1500);
    Assert.assertNotSame("Objects should not be the same", fiveHundred, oneThousand);
    Assert.assertNotSame("Objects should not be the same", fiveHundred, fifteenHundred);
    Assert.assertNotSame("Objects should not be the same", oneThousand, fifteenHundred);
  }

  @Test
  public void sameOverdraftsAreSameObjects() {
    var fiveHundredA = PooledOverdraftWithdrawalApprovalStrategy.borrowing(500);
    var fiveHundredB = PooledOverdraftWithdrawalApprovalStrategy.borrowing(500);
    Assert.assertSame("Objects ahould be the same", fiveHundredA, fiveHundredB);
  }
}
