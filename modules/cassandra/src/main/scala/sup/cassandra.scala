package sup

import cats.Id
import cats.effect.Blocker
import cats.effect.ContextShift
import cats.effect.Sync
import com.datastax.oss.driver.api.core.CqlSession

object cassandra {

  def connectionCheck[F[_]: Sync: ContextShift](session: CqlSession, blocker: Blocker): HealthCheck[F, Id] =
    HealthCheck.liftFBoolean {
      blocker.delay(session.execute("SELECT now() FROM system.local").isFullyFetched)
    }
}